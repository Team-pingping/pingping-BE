package com.pingping.share.application

import com.pingping.share.domain.ShareUrl
import com.pingping.share.dto.request.ShareUrlRequest
import com.pingping.share.dto.response.ShareUrlResponse
import org.springframework.stereotype.Component

@Component
class ShareUrlMapper {

    fun toEntity(request: ShareUrlRequest, url: String): ShareUrl {
        return ShareUrl(url, request.eventName, request.neighborhood)
    }

    fun toResponse(entity: ShareUrl): ShareUrlResponse {
        return ShareUrlResponse(entity.id, entity.url, entity.eventName, entity.neighborhood)
    }
}