package com.ping.application.member.service

import com.ping.application.member.dto.GetNonMemberProfile
import com.ping.application.member.dto.LoginNonMember
import com.ping.application.member.dto.LoginTokenNonMember
import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.common.util.ValidationUtil
import com.ping.domain.member.repository.NonMemberRepository
import com.ping.domain.ping.repository.NonMemberBookmarkUrlRepository
import com.ping.domain.ping.repository.NonMemberStoreUrlRepository
import com.ping.support.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NonMemberService(
    private val nonMemberRepository: NonMemberRepository,
    private val nonMemberBookmarkUrlRepository: NonMemberBookmarkUrlRepository,
    private val nonMemberStoreUrlRepository: NonMemberStoreUrlRepository,
    private val jwtProvider: JwtProvider,
) {
    fun login(request: LoginNonMember.Request): LoginNonMember.Response {
        ValidationUtil.validatePassword(request.password)

        val nonMember = nonMemberRepository.findById(request.nonMemberId)
            ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)

        if (request.password != nonMember.password) {
            throw CustomException(ExceptionContent.NON_MEMBER_LOGIN_FAILED)
        }

        val savedBookmarkUrls =
            nonMemberBookmarkUrlRepository.findAllByNonMemberId(request.nonMemberId).map { it.bookmarkUrl }
        val savedStoreUrls = nonMemberStoreUrlRepository.findAllByNonMemberId(request.nonMemberId).map { it.storeUrl }

        return LoginNonMember.Response(
            nonMemberId = nonMember.id,
            name = nonMember.name,
            bookmarkUrls = savedBookmarkUrls,
            storeUrls = savedStoreUrls,
        )
    }

    fun loginWithToken(request: LoginNonMember.Request): LoginTokenNonMember.Response {
        ValidationUtil.validatePassword(request.password)

        val nonMember = nonMemberRepository.findById(request.nonMemberId)
            ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)

        if (request.password != nonMember.password) {
            throw CustomException(ExceptionContent.NON_MEMBER_LOGIN_FAILED)
        }

        val token = jwtProvider.issue(nonMember.id)

        return LoginTokenNonMember.Response(
            nonMemberId = nonMember.id,
            name = nonMember.name,
            accessToken = token,
        )
    }

    fun getNonMemberProfile(nonmemberId: Long): GetNonMemberProfile.Response {
        val nonMember = nonMemberRepository.findById(nonmemberId)
            ?: throw CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND)
        return GetNonMemberProfile.Response(
            name = nonMember.name,
            profileSvg = nonMember.profileSvg,
            profileLockSvg = nonMember.profileLockSvg
        )
    }
}