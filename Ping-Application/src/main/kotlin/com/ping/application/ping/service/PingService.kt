package com.ping.application.ping.service

import com.ping.application.member.dto.CreateNonMember
import com.ping.application.ping.dto.*
import com.ping.common.enums.PlaceType
import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.common.util.ValidationUtil
import com.ping.domain.event.aggregate.ShareUrlDomain
import com.ping.domain.event.repository.ShareUrlRepository
import com.ping.domain.member.aggregate.NonMemberDomain
import com.ping.domain.member.repository.NonMemberRepository
import com.ping.domain.member.repository.ProfileRepository
import com.ping.domain.ping.aggregate.*
import com.ping.domain.ping.repository.*
import com.ping.domain.ping.service.PingUrlService
import com.ping.support.jwt.JwtProvider
import com.ping.support.jwt.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class PingService(
    private val nonMemberRepository: NonMemberRepository,
    private val shareUrlRepository: ShareUrlRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val nonMemberPlaceRepository: NonMemberPlaceRepository,
    private val nonMemberBookmarkUrlRepository: NonMemberBookmarkUrlRepository,
    private val nonMemberStoreUrlRepository: NonMemberStoreUrlRepository,
    private val profileRepository: ProfileRepository,
    private val recommendPlaceRepository: RecommendPlaceRepository,
    private val pingUrlService: PingUrlService,
    private val jwtProvider: JwtProvider,
    private val jwtUtil: JwtUtil,
) {

    @Transactional
    fun createNonMemberPings(request: CreateNonMember.Request) {
        ValidationUtil.validateName(request.name)
        ValidationUtil.validatePassword(request.password)

        val shareUrl = shareUrlRepository.findByUuid(request.uuid)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)

        nonMemberRepository.findByShareUrlIdAndName(shareUrl.id, request.name)?.let {
            throw CustomException(ExceptionContent.NON_MEMBER_ALREADY_EXISTS)
        }
        val profile = getRandomProfileSvg()
        val nonMemberDomain = NonMemberDomain.of(
            name = request.name,
            password = request.password,
            profileSvg = profile.first,
            profileLockSvg = profile.second,
            shareUrlDomain = shareUrl
        )
        val savedNonMember = nonMemberRepository.save(nonMemberDomain)

        val nonMemberBookmarkUrlDomains = NonMemberBookmarkUrlDomain.of(savedNonMember, request.bookmarkUrls)
        val nonMemberStoreUrlDomains = NonMemberStoreUrlDomain.of(savedNonMember, request.storeUrls)
        nonMemberBookmarkUrlRepository.saveAll(nonMemberBookmarkUrlDomains)
        nonMemberStoreUrlRepository.saveAll(nonMemberStoreUrlDomains)

        val bookmarkSids = handleBookmarkUrls(request.bookmarkUrls)
        val storeSids = handleStoreUrls(request.storeUrls)
        val allSids = bookmarkSids + storeSids

        if (allSids.isNotEmpty()) {
            val updatedShareUrl = shareUrl.copy(pingUpdateTime = LocalDateTime.now())
            shareUrlRepository.save(updatedShareUrl)
        }

        val nonMemberPlaces = allSids.map { sid -> NonMemberPlaceDomain.of(savedNonMember, sid, PlaceType.CRAWLED) }
        nonMemberPlaceRepository.saveAll(nonMemberPlaces)
    }

    @Transactional
    fun saveMemberPing(request: MemberPing.Request, httpRequest: HttpServletRequest) {
        jwtProvider.validateToken(httpRequest)
        val nonMemberId = jwtUtil.getNonMemberId(httpRequest)
        val sid = request.sid

        val nonMember = NonMemberDomain.validateExists(nonMemberId, nonMemberRepository)
        BookmarkDomain.validateSidExists(sid, bookmarkRepository)
        NonMemberPlaceDomain.validateNoDuplicate(nonMemberId, sid, nonMemberPlaceRepository)

        val newPlace = NonMemberPlaceDomain.of(
            nonMember = nonMember,
            sid = sid,
            placeType = PlaceType.MEMBER_ADDED
        )
        nonMemberPlaceRepository.save(newPlace)
    }

    @Transactional
    fun saveRecommendPings(request: SaveRecommendPings.Request): GetAllNonMemberPings.Response {
        val shareUrl = shareUrlRepository.findByUuid(request.uuid)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)

        val recommendPlaces = request.sids
            .map { sid ->
                RecommendPlaceDomain.of(shareUrl, sid)
            }

        val savedRecommendPlaces = recommendPlaceRepository.saveAll(recommendPlaces)
        val nonMemberList = nonMemberRepository.findAllByShareUrl(shareUrl.id)

        return createPingResponse(shareUrl, savedRecommendPlaces, nonMemberList)
    }

    private fun getRandomProfileSvg(): Pair<String, String> {
        val profiles = profileRepository.findAll()
        val index = Random.nextInt(profiles.size)
        return Pair(profiles[index].svgUrl, profiles[index].svgLockUrl)
    }

    fun getAllNonMemberPings(uuid: String): GetAllNonMemberPings.Response {
        val shareUrl = shareUrlRepository.findByUuid(uuid)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)
        val nonMemberList = nonMemberRepository.findAllByShareUrl(shareUrl.id)

        val savedRecommendPlaces = recommendPlaceRepository.findAllByShareUrlId(shareUrl.id)

        return createPingResponse(shareUrl, savedRecommendPlaces, nonMemberList)
    }

    fun getNonMemberPing(nonMemberId: Long): GetNonMemberPing.Response {
        val nonMember = nonMemberRepository.findById(nonMemberId)
            ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)
        val nonMemberPlaces = nonMemberPlaceRepository.findAllByNonMemberId(nonMemberId)
        val bookmarks = bookmarkRepository.findAllBySidIn(nonMemberPlaces.map { it.sid })
        return GetNonMemberPing.Response(
            pings = bookmarks.map {
                val nonMembers = nonMemberRepository.findAllBySidAndShareUrlId(it.sid, nonMember.shareUrlDomain.id)
                GetAllNonMemberPings.Ping(
                    iconLevel = 0,
                    sid = it.sid,
                    nonMembers = nonMembers.map { nonMember ->
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = nonMember.id,
                            name = nonMember.name,
                            profileSvg = nonMember.profileSvg,
                        )
                    },
                    url = it.url,
                    placeName = it.name,
                    px = it.px,
                    py = it.py,
                    type = it.mcidName,
                )
            }
        )
    }

    fun getRecommendPings(uuid: String, radiusInKm: Double): GetRecommendPings.Response {
        val shareUrl = shareUrlRepository.findByUuid(uuid)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)

        val nonMemberIds = nonMemberRepository.findAllByShareUrl(shareUrl.id).map { it.id }
        val excludedSids = nonMemberPlaceRepository.findAllByNonMemberIdIn(nonMemberIds).map { it.sid }.toSet()

        val bookmarks = bookmarkRepository.findAllByLocationNear(shareUrl.px, shareUrl.py, radiusInKm)
        val nearbySids = bookmarks.map { it.sid }

        val sidCounts = nonMemberPlaceRepository.findCountBySidIn(nearbySids)
        val sidCountsMap = sidCounts.associate { it.sid to it.count }

        val recommendSids = sidCountsMap.entries
            .filter { it.key !in excludedSids }
            .sortedByDescending { it.value }
            .take(5)
            .map { it.key }

        val recommendPings = recommendSids.mapNotNull { sid ->
            bookmarks.find { it.sid == sid }?.let { bookmark ->
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = bookmark.sid,
                    placeName = bookmark.name,
                    url = bookmark.url,
                    px = bookmark.px,
                    py = bookmark.py,
                    type = bookmark.mcidName,
                )
            }
        }

        return GetRecommendPings.Response(recommendPings)
    }

    @Transactional
    fun updateNonMemberPings(request: UpdateNonMemberPings.Request) {
        val nonMember = nonMemberRepository.findById(request.nonMemberId)
            ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)

        val bookmarkUrlSids = handleBookmarkUrls(request.bookmarkUrls)
        val storeUrlSids = handleStoreUrls(request.storeUrls)
        val newSids = (bookmarkUrlSids + storeUrlSids)

        updateNonMemberPlacesIfNeeded(nonMember, newSids)
        updateBookmarkUrls(nonMember, request.bookmarkUrls)
        updateStoreUrls(nonMember, request.storeUrls)
    }

    @Transactional
    fun refreshAllNonMemberPings(uuid: String): GetAllNonMemberPings.Response {
        val shareUrl = shareUrlRepository.findByUuid(uuid)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)

        val nonMemberList = nonMemberRepository.findAllByShareUrl(shareUrl.id)

        nonMemberList.forEach { nonMember ->
            val existingBookmarkUrls =
                nonMemberBookmarkUrlRepository.findAllByNonMemberId(nonMember.id).map { it.bookmarkUrl }
            val existingStoreUrls = nonMemberStoreUrlRepository.findAllByNonMemberId(nonMember.id).map { it.storeUrl }

            val bookmarkUrlSids = handleBookmarkUrls(existingBookmarkUrls)
            val storeUrlSids = handleStoreUrls(existingStoreUrls)
            val newSids = (bookmarkUrlSids + storeUrlSids)

            updateNonMemberPlacesIfNeeded(nonMember, newSids)
        }

        val savedRecommendPlaces = recommendPlaceRepository.findAllByShareUrlId(shareUrl.id)

        return createPingResponse(shareUrl, savedRecommendPlaces, nonMemberList)
    }

    @Transactional
    fun deletePing(request: MemberPing.Request, httpRequest: HttpServletRequest) {
        jwtProvider.validateToken(httpRequest)
        val nonMemberId = jwtUtil.getNonMemberId(httpRequest)

        NonMemberDomain.validateExists(nonMemberId, nonMemberRepository)
        val nonMemberPlace = NonMemberPlaceDomain.validateExists(nonMemberId, request.sid, nonMemberPlaceRepository)

        nonMemberPlaceRepository.delete(nonMemberPlace.id)
    }

    private fun handleBookmarkUrls(
        bookmarkUrls: List<String>
    ): Set<String> {
        val bookmarks = mutableListOf<BookmarkDomain>()

        bookmarkUrls.forEach {
            bookmarks.addAll(pingUrlService.bookmarkUrlToBookmarks(it))
        }
        bookmarkRepository.saveAll(bookmarks)
        return bookmarks.map { it.sid }.toSet()
    }

    private fun handleStoreUrls(
        storeUrls: List<String>
    ): Set<String> {
        val bookmarks = mutableListOf<BookmarkDomain>()

        storeUrls.forEach {
            bookmarks.add(pingUrlService.storeUrlToBookmark(it))
        }
        bookmarkRepository.saveAll(bookmarks)
        return bookmarks.map { it.sid }.toSet()
    }

    private fun updateNonMemberPlacesIfNeeded(nonMember: NonMemberDomain, newSids: Set<String>) {
        val nonMemberPlaces = nonMemberPlaceRepository.findAllByNonMemberId(nonMember.id)
        val existingSids = NonMemberPlaceDomain.filterCrawledPlaces(nonMemberPlaces)

        if (existingSids != newSids) {
            val sidsToAdd = newSids - existingSids
            val sidsToDelete = existingSids - newSids

            val placesToAdd = sidsToAdd.map { sid ->
                NonMemberPlaceDomain.of(
                    nonMember = nonMember,
                    sid = sid,
                    placeType = PlaceType.CRAWLED
                )
            }
            nonMemberPlaceRepository.saveAll(placesToAdd)

            val placesIdToDelete = NonMemberPlaceDomain.filterIdsToDelete(
                domains = nonMemberPlaces,
                nonMember = nonMember,
                sidsToDelete = sidsToDelete
            )
            nonMemberPlaceRepository.deleteAll(placesIdToDelete)

            val updatedShareUrl = nonMember.shareUrlDomain.copy(
                pingUpdateTime = LocalDateTime.now()
            )
            shareUrlRepository.save(updatedShareUrl)
        }
    }

    private fun updateBookmarkUrls(nonMember: NonMemberDomain, bookmarkUrls: List<String>) {
        val existingBookmarks = nonMemberBookmarkUrlRepository.findAllByNonMemberId(nonMember.id)

        val (urlsToAdd, idsToDelete) = findUrlsToUpdate(
            existingUrls = existingBookmarks,
            newUrls = bookmarkUrls,
            getUrl = { it.bookmarkUrl },
            getId = { it.id })

        nonMemberBookmarkUrlRepository.saveAll(NonMemberBookmarkUrlDomain.of(nonMember, urlsToAdd))
        nonMemberBookmarkUrlRepository.deleteAllByIds(idsToDelete)
    }

    private fun updateStoreUrls(nonMember: NonMemberDomain, storeUrls: List<String>) {
        val existingStoreUrls = nonMemberStoreUrlRepository.findAllByNonMemberId(nonMember.id)

        val (urlsToAdd, idsToDelete) = findUrlsToUpdate(
            existingUrls = existingStoreUrls,
            newUrls = storeUrls,
            getUrl = { it.storeUrl },
            getId = { it.id }
        )

        nonMemberStoreUrlRepository.saveAll(NonMemberStoreUrlDomain.of(nonMember, urlsToAdd))
        nonMemberStoreUrlRepository.deleteAllByIds(idsToDelete)
    }

    private fun <T> findUrlsToUpdate(
        existingUrls: List<T>,
        newUrls: List<String>,
        getUrl: (T) -> String,
        getId: (T) -> Long
    ): Pair<List<String>, List<Long>> {
        val existingUrlSet = existingUrls.map { getUrl(it) }.toSet()
        val urlsToAdd = newUrls.filter { it !in existingUrlSet }
        val idsToDelete = existingUrls.filter { getUrl(it) !in newUrls }.map { getId(it) }

        return Pair(urlsToAdd, idsToDelete)
    }

    private fun createPingResponse(
        shareUrl: ShareUrlDomain,
        recommendPlaces: List<RecommendPlaceDomain>,
        nonMemberList: List<NonMemberDomain>
    ): GetAllNonMemberPings.Response {
        val pingLastUpdateTime = calculateTimeDifference(shareUrl)

        val recommendSids = recommendPlaces.map { it.sid }
        val recommendBookmarks = bookmarkRepository.findAllBySidIn(recommendSids)

        val bookmarkMap = recommendBookmarks.associateBy { it.sid }

        val recommendPings = recommendPlaces.mapNotNull { recommendPlace ->
            val bookmark = bookmarkMap[recommendPlace.sid]
            bookmark?.let {
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = recommendPlace.sid,
                    placeName = it.name,
                    url = it.url,
                    px = it.px,
                    py = it.py,
                    type = it.mcidName
                )
            }
        }

        val nonMembers = nonMemberList.map { nonMember ->
            GetAllNonMemberPings.NonMember(
                nonMemberId = nonMember.id,
                name = nonMember.name,
                profileSvg = nonMember.profileSvg
            )
        }

        val nonMemberPlaces = nonMembersToNonMemberPlacesMap(nonMemberList)
        val pings = nonMemberPlaces.entries.flatMapIndexed { index, nonMemberPlace ->
            val level = calculateIconLevel(index, nonMemberPlace.key)
            nonMemberPlace.value.map { bookmarkPair ->
                GetAllNonMemberPings.Ping(
                    iconLevel = level,
                    sid = bookmarkPair.first.sid,
                    nonMembers = bookmarkPair.second.map {
                        GetAllNonMemberPings.NonMember(nonMemberId = it.id, name = it.name, profileSvg = it.profileSvg)
                    },
                    url = bookmarkPair.first.url,
                    placeName = bookmarkPair.first.name,
                    px = bookmarkPair.first.px,
                    py = bookmarkPair.first.py,
                    type = bookmarkPair.first.mcidName
                )
            }
        }

        return GetAllNonMemberPings.Response(
            eventName = shareUrl.eventName,
            neighborhood = shareUrl.neighborhood,
            px = shareUrl.px,
            py = shareUrl.py,
            pingLastUpdateTime = pingLastUpdateTime,
            recommendPings = recommendPings,
            nonMembers = nonMembers,
            pings = pings,
        )
    }

    private fun calculateIconLevel(index: Int, overlapCount: Int): Int {
        val mostOverlappedIconLevel = 4
        val secondOverlappedIconLevel = 3
        val thirdOverlappedIconLevel = 2
        val remainderIconLevel = 1
        return when {
            overlapCount == 1 -> remainderIconLevel
            index == 0 -> mostOverlappedIconLevel
            index == 1 -> secondOverlappedIconLevel
            index == 2 -> thirdOverlappedIconLevel
            else -> remainderIconLevel
        }
    }

    private fun nonMembersToNonMemberPlacesMap(nonMembers: List<NonMemberDomain>): Map<Int, List<Pair<BookmarkDomain, List<NonMemberDomain>>>> {
        //list<Pair<NonMemberPlaceDomain, List<NonMemberDomain>>>
        val allNonMemberPlaces = nonMembers.flatMap { nonMember ->
            nonMemberPlaceRepository.findAllByNonMemberId(nonMember.id).map { place ->
                place to nonMember
            }
        }

        val bookmarks = bookmarkRepository.findAllBySidIn(allNonMemberPlaces.map { it.first.sid }.distinct())
        val bookmarkMap = bookmarks.associateBy { it.sid }

        //Map<Int(count),List<Pair<BookmarkDomain,List<NonMemberDomain>>>>
        val nonMemberPlaces = allNonMemberPlaces
            .groupBy { it.first.sid }
            .mapNotNull { (sid, placeNonMemberPairs) ->
                val bookmarkDomain = bookmarkMap[sid]
                bookmarkDomain?.let {
                    it to placeNonMemberPairs.map { placeNonMemberPair ->
                        placeNonMemberPair.second
                    }
                }
            }.sortedByDescending { it.second.size }.groupBy { it.second.size }
        return nonMemberPlaces
    }

    private fun calculateTimeDifference(shareUrl: ShareUrlDomain): String? {
        val pingLastUpdateTime: String? = shareUrl.pingUpdateTime?.let {
            val timeDifference = Duration.between(shareUrl.pingUpdateTime, LocalDateTime.now())
            when {
                timeDifference.toDays() >= 1 -> "${timeDifference.toDays()}일"
                timeDifference.toHours() >= 1 -> "${timeDifference.toHours()}시간"
                timeDifference.toMinutes() >= 1 -> "${timeDifference.toMinutes()}분"
                else -> "1분"
            }
        }
        return pingLastUpdateTime
    }
}