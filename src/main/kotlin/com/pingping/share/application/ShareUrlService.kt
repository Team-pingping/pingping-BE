package com.pingping.share.application

import com.pingping.share.domain.repository.ShareUrlRepository
import com.pingping.share.dto.request.ShareUrlRequest
import com.pingping.share.dto.response.ShareUrlResponse
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ShareUrlService(
    private val shareUrlRepository: ShareUrlRepository,
    private val shareUrlMapper: ShareUrlMapper
) {
    @Value("\${pingping.share.base-url}")
    private lateinit var baseUrl: String

    // 공유 URL 생성 로직
    @Transactional
    fun createShareUrl(request: ShareUrlRequest): ShareUrlResponse {
        val uniqueUrl = generateUniqueUrl(request)

        val shareUrl = shareUrlMapper.toEntity(request, uniqueUrl)
        val savedShareUrl = shareUrlRepository.save(shareUrl)

        return shareUrlMapper.toResponse(savedShareUrl)
    }

    // 고유 URL 생성 로직
    private fun generateUniqueUrl(request: ShareUrlRequest): String {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8)
        return "$baseUrl/${request.eventName}/${request.neighborhood}/$uniqueId"
    }
}