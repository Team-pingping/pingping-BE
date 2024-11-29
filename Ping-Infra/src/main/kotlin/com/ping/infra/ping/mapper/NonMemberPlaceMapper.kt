package com.ping.infra.ping.mapper

import com.ping.domain.ping.aggregate.NonMemberPlaceDomain
import com.ping.infra.member.mapper.NonMemberMapper
import com.ping.infra.ping.jpa.entity.NonMemberPlaceEntity

object NonMemberPlaceMapper {

    fun toDomain(nonMemberPlaceEntity: NonMemberPlaceEntity) = NonMemberPlaceDomain(
        id = nonMemberPlaceEntity.id,
        nonMember = NonMemberMapper.toDomain(nonMemberPlaceEntity.nonMember),
        sid = nonMemberPlaceEntity.sid,
        placeType = nonMemberPlaceEntity.placeType
    )

    fun toEntity(nonMemberPlaceDomain: NonMemberPlaceDomain) = NonMemberPlaceEntity(
        id = nonMemberPlaceDomain.id,
        nonMember = NonMemberMapper.toEntity(nonMemberPlaceDomain.nonMember),
        sid = nonMemberPlaceDomain.sid,
        placeType = nonMemberPlaceDomain.placeType
    )
}