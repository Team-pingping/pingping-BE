package com.ping.domain.member.aggregate

import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.domain.event.aggregate.ShareUrlDomain
import com.ping.domain.member.repository.NonMemberRepository

data class NonMemberDomain(
    val id: Long,
    val name: String,
    val password: String,
    val profileSvg: String,
    val profileLockSvg: String,
    val shareUrlDomain: ShareUrlDomain
) {
    companion object {
        fun of(name: String,
            password: String,
            profileSvg: String,
            profileLockSvg: String,
            shareUrlDomain: ShareUrlDomain,
        ) = NonMemberDomain(0L, name, password, profileSvg, profileLockSvg, shareUrlDomain)

        fun validateExists(nonMemberId: Long, nonMemberRepository: NonMemberRepository): NonMemberDomain {
            return nonMemberRepository.findById(nonMemberId)
                ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)
        }
    }
}