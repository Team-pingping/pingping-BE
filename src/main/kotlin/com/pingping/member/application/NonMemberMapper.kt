package com.pingping.member.application

import com.pingping.member.domain.NonMember
import com.pingping.member.dto.request.NonMemberCreateRequest
import com.pingping.share.domain.ShareUrl
import org.springframework.stereotype.Component

@Component
class NonMemberMapper {

    // NonMemberCreateRequest -> NonMember 엔티티 변환
    fun toEntity(request: NonMemberCreateRequest, shareUrl: ShareUrl): NonMember {
        return NonMember(request.name, request.password, shareUrl)
    }
}