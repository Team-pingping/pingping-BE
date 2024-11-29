package com.ping.infra.ping.mapper

import com.ping.domain.ping.aggregate.NonMemberBookmarkUrlDomain
import com.ping.infra.member.mapper.NonMemberMapper
import com.ping.infra.ping.jpa.entity.NonMemberBookmarkUrlEntity

object NonMemberBookmarkUrlMapper {

    fun toDomain(nonMemberBookUrlEntity: NonMemberBookmarkUrlEntity) = NonMemberBookmarkUrlDomain(
        nonMemberBookUrlEntity.id,
        NonMemberMapper.toDomain(nonMemberBookUrlEntity.nonMember),
        nonMemberBookUrlEntity.bookmarkUrl
    )

    fun toEntity(nonMemberBookUrlDomain: NonMemberBookmarkUrlDomain) = NonMemberBookmarkUrlEntity(
        nonMemberBookUrlDomain.id,
        NonMemberMapper.toEntity(nonMemberBookUrlDomain.nonMember),
        nonMemberBookUrlDomain.bookmarkUrl
    )
}