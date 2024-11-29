package com.ping.domain.ping.aggregate

import com.ping.common.enums.PlaceType
import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.domain.member.aggregate.NonMemberDomain
import com.ping.domain.ping.repository.NonMemberPlaceRepository

data class NonMemberPlaceDomain(
    val id: Long,
    val nonMember: NonMemberDomain,
    val sid: String,
    val placeType: PlaceType
) {
    companion object {
        fun of(nonMember: NonMemberDomain, sid: String, placeType: PlaceType) =
            NonMemberPlaceDomain(
                id = 0L,
                nonMember = nonMember,
                sid = sid,
                placeType = placeType
            )

        fun filterCrawledPlaces(domains: List<NonMemberPlaceDomain>): Set<String> {
            return domains
                .filter { it.placeType == PlaceType.CRAWLED }
                .map { it.sid }
                .toSet()
        }

        fun filterIdsToDelete(
            domains: List<NonMemberPlaceDomain>,
            nonMember: NonMemberDomain,
            sidsToDelete: Set<String>
        ): List<Long> {
            return domains
                .filter { it.nonMember == nonMember && it.sid in sidsToDelete }
                .map { it.id }
        }

        fun validateExists(nonMemberId: Long, sid: String, nonMemberPlaceRepository: NonMemberPlaceRepository): NonMemberPlaceDomain {
            return nonMemberPlaceRepository.findByNonMemberIdAndSid(nonMemberId, sid)
                ?: throw CustomException(ExceptionContent.NON_MEMBER_PLACE_NOT_FOUND)
        }

        fun validateNoDuplicate(nonMemberId: Long, sid: String, nonMemberPlaceRepository: NonMemberPlaceRepository) {
            val existingPlace = nonMemberPlaceRepository.findByNonMemberIdAndSid(nonMemberId, sid)
            if (existingPlace != null) {
                throw CustomException(ExceptionContent.NON_MEMBER_PLACE_ALREADY_EXISTS)
            }
        }
    }
}