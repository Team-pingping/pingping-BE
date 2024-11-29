package com.ping.infra.ping.mapper

import com.ping.domain.ping.aggregate.RecommendPlaceDomain
import com.ping.infra.event.mapper.ShareUrlMapper
import com.ping.infra.ping.jpa.entity.RecommendPlaceEntity

object RecommendPlaceMapper {
    fun toDomain(recommendPlaceEntity: RecommendPlaceEntity) = RecommendPlaceDomain(
        id = recommendPlaceEntity.id,
        shareUrlDomain = ShareUrlMapper.toDomain(recommendPlaceEntity.shareUrl),
        sid = recommendPlaceEntity.sid,
    )

    fun toEntity(recommendPlaceDomain: RecommendPlaceDomain) = RecommendPlaceEntity(
        id = recommendPlaceDomain.id,
        shareUrl = ShareUrlMapper.toEntity(recommendPlaceDomain.shareUrlDomain),
        sid = recommendPlaceDomain.sid,
    )
}