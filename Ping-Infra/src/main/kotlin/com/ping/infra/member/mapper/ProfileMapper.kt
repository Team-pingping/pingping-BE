package com.ping.infra.member.mapper

import com.ping.domain.member.aggregate.ProfileDomain
import com.ping.infra.member.mongo.entity.ProfileEntity

object ProfileMapper {

    fun toDomain(profileEntity: ProfileEntity) = ProfileDomain(
        profileEntity.id,
        profileEntity.svgUrl,
        profileEntity.lockSvgUrl
    )

    fun toEntity(profileDomain: ProfileDomain) = ProfileEntity(
        profileDomain.id,
        profileDomain.svgUrl,
        profileDomain.svgLockUrl
    )
}