package com.ping.infra.event.mapper

import com.ping.domain.event.aggregate.SubwayDomain
import com.ping.infra.event.mongo.entity.SubwayEntity

object SubwayMapper {

    fun toEntity(subwayDomain: SubwayDomain) = SubwayEntity(
        subwayDomain.id,
        subwayDomain.station
    )

    fun toDomain(subwayEntity: SubwayEntity) = SubwayDomain(
        subwayEntity.id,
        subwayEntity.station
    )
}