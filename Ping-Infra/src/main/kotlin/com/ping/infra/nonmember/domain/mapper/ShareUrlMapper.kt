package com.ping.infra.nonmember.domain.mapper

import com.ping.domain.nonmember.aggregate.ShareUrl
import com.ping.infra.nonmember.domain.jpa.entity.ShareUrlEntity

class ShareUrlMapper {
    companion object {
        fun toEntity(shareUrl: ShareUrl): ShareUrlEntity {
            return ShareUrlEntity(
                shareUrl.url,
                shareUrl.eventName,
                shareUrl.neighborhood
            )
        }
        fun toDomain(shareUrlEntity: ShareUrlEntity): ShareUrl {
            return ShareUrl(
                shareUrlEntity.id,
                shareUrlEntity.url,
                shareUrlEntity.eventName,
                shareUrlEntity.neighborhood
            )
        }
    }
}