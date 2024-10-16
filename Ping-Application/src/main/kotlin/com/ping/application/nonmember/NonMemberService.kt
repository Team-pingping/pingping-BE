package com.ping.application.nonmember

import com.ping.application.nonmember.dto.request.NonMemberCreateRequest
import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.domain.nonmember.aggregate.NonMember
import com.ping.domain.nonmember.repository.NonMemberRepository
import com.ping.domain.nonmember.repository.ShareUrlRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NonMemberService(
    private val nonMemberRepository: NonMemberRepository,
    private val shareUrlRepository: ShareUrlRepository,
) {

    @Transactional
    fun createNonMember(request: NonMemberCreateRequest): Long {
        // 비밀번호 형식 검사 (4자리 숫자)
        validatePassword(request.password)

        val shareUrl = shareUrlRepository.findById(request.shareUrlId)
            ?: throw CustomException(ExceptionContent.INVALID_SHARE_URL)

        // shareUrlId과 name으로 비회원 존재 여부 확인
        nonMemberRepository.findByShareUrlIdAndName(request.shareUrlId, request.name)?.let {
            throw CustomException(ExceptionContent.NON_MEMBER_ALREADY_EXISTS)
        }

        // NonMember 엔티티 생성 및 저장
        val nonMember = NonMember.of(request.name, request.password, shareUrl)
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