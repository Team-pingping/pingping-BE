package com.ping.api.ping

import com.ping.application.member.dto.CreateNonMember
import com.ping.application.ping.dto.*
import com.ping.application.ping.service.PingService
import com.ping.domain.ping.service.PingUrlService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
class PingController(
    private val pingService: PingService,
    private val pingUrlService: PingUrlService,
) {
    @PostMapping(PingApi.BASE_URL)
    fun createNonMemberPings(@RequestBody request: CreateNonMember.Request) {
        return pingService.createNonMemberPings(request)
    }

    @PostMapping(PingApi.PING_MEMBER)
    fun saveMemberPing(@RequestBody request: MemberPing.Request, httpRequest: HttpServletRequest) {
        pingService.saveMemberPing(request, httpRequest)
    }

    @PostMapping(PingApi.PING_RECOMMEND)
    fun saveRecommendPings(@RequestBody request: SaveRecommendPings.Request): GetAllNonMemberPings.Response {
        return pingService.saveRecommendPings(request)
    }

    @GetMapping(PingApi.BASE_URL)
    fun getNonMemberPings(@RequestParam uuid: String): GetAllNonMemberPings.Response {
        return pingService.getAllNonMemberPings(uuid)
    }

    @GetMapping(PingApi.PING_NONMEMBER_ID)
    fun getNonMemberPing(@PathVariable nonMemberId: Long): GetNonMemberPing.Response {
        return pingService.getNonMemberPing(nonMemberId)
    }

    @GetMapping(PingApi.PING_RECOMMEND)
    fun getRecommendPings(@RequestParam uuid: String, @RequestParam radiusInKm: Double): GetRecommendPings.Response {
        return pingService.getRecommendPings(uuid, radiusInKm)
    }

    @GetMapping(PingApi.PING_REFRESH_ALL)
    fun refreshAllNonMemberPings(@RequestParam uuid: String): GetAllNonMemberPings.Response {
        return pingService.refreshAllNonMemberPings(uuid)
    }

    @PutMapping(PingApi.BASE_URL)
    fun updateNonMemberPings(@RequestBody request: UpdateNonMemberPings.Request) {
        pingService.updateNonMemberPings(request)
    }

    @PutMapping(PingApi.BOOKMARK)
    fun isCorrectBookmarkUrl(@RequestBody isCorrectUrl: IsCorrectUrl.Request) {
        pingUrlService.bookmarkUrlToBookmarks(isCorrectUrl.url)
    }

    @PutMapping(PingApi.STORE)
    fun isCorrectStoreUrl(@RequestBody isCorrectUrl: IsCorrectUrl.Request) {
        pingUrlService.storeUrlToBookmark(isCorrectUrl.url)
    }

    @DeleteMapping(PingApi.PING_MEMBER)
    fun deletePing(@RequestBody request: MemberPing.Request, httpRequest: HttpServletRequest) {
        pingService.deletePing(request, httpRequest)
    }
}