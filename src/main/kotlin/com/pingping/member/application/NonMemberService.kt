package com.pingping.member.application

import com.pingping.global.exception.CustomException
import com.pingping.global.exception.ExceptionContent
import com.pingping.member.domain.repository.NonMemberRepository
import com.pingping.member.dto.request.NonMemberCreateRequest
import com.pingping.member.dto.request.NonMemberLoginRequest
import com.pingping.member.dto.response.NonMemberLoginResponse
import com.pingping.share.domain.repository.ShareUrlRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NonMemberService(
    private val nonMemberRepository: NonMemberRepository,
    private val shareUrlRepository: ShareUrlRepository,
    private val nonMemberMapper: NonMemberMapper
) {
    fun loginNonMember(request: NonMemberLoginRequest): NonMemberLoginResponse {
        // 비밀번호 형식 검사 (4자리 숫자)
        validatePassword(request.password)

        val nonMember = nonMemberRepository.findById(request.nonMemberId)
            .orElseThrow { CustomException(ExceptionContent.NON_MEMBER_NOT_FOUND) }

        // 비밀번호가 일치하는지 비교
        if (request.password != nonMember.password) {
            throw CustomException(ExceptionContent.NON_MEMBER_LOGIN_FAILED)
        }

        return NonMemberLoginResponse(nonMember.id)
    }

    @Transactional
    fun createNonMember(request: NonMemberCreateRequest): Long {
        // 비밀번호 형식 검사 (4자리 숫자)
        validatePassword(request.password)

        val shareUrl = shareUrlRepository.findById(request.shareUrlId)
            .orElseThrow { CustomException(ExceptionContent.INVALID_SHARE_URL) }

        // shareUrlId과 name으로 비회원 존재 여부 확인
        nonMemberRepository.findByShareUrlIdAndName(request.shareUrlId, request.name)?.let {
            throw CustomException(ExceptionContent.NON_MEMBER_ALREADY_EXISTS)
        }

        // NonMember 엔티티 생성 및 저장
        val nonMember = nonMemberMapper.toEntity(request, shareUrl)
        val savedNonMember = nonMemberRepository.save(nonMember)

        return savedNonMember.id
    }

    // 비밀번호 유효성 검증 로직
    private fun validatePassword(password: String) {
        if (!password.matches(Regex("\\d{4}"))) {
            throw CustomException(ExceptionContent.INVALID_PASSWORD_FORMAT)
        }
    }

}