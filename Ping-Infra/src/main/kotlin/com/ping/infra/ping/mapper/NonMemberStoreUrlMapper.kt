package com.ping.infra.ping.mapper

import com.ping.domain.ping.aggregate.NonMemberStoreUrlDomain
import com.ping.infra.member.mapper.NonMemberMapper
import com.ping.infra.ping.jpa.entity.NonMemberStoreUrlEntity

object NonMemberStoreUrlMapper {
    
    fun toDomain(nonMemberStoreUrlEntity: NonMemberStoreUrlEntity) = NonMemberStoreUrlDomain(
        nonMemberStoreUrlEntity.id,
        NonMemberMapper.toDomain(nonMemberStoreUrlEntity.nonMember),
        nonMemberStoreUrlEntity.storeUrl
    )

    fun toEntity(nonMemberStoreUrlDomain: NonMemberStoreUrlDomain) = NonMemberStoreUrlEntity(
        nonMemberStoreUrlDomain.id,
        NonMemberMapper.toEntity(nonMemberStoreUrlDomain.nonMember),
        nonMemberStoreUrlDomain.storeUrl
    )
}