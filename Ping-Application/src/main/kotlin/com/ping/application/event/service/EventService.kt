package com.ping.application.event.service

import com.ping.application.event.dto.CreateEvent
import com.ping.domain.event.aggregate.ShareUrlDomain
import com.ping.domain.event.repository.ShareUrlRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class EventService(
    private val shareUrlRepository: ShareUrlRepository
) {
    @Value("\${pingping.share.base-url}")
    private lateinit var baseUrl: String

    @Transactional
    fun create(request: CreateEvent.Request): CreateEvent.Response {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8)
        val uniqueUrl = generateUniqueUrl(request, uniqueId)

        val shareUrl = ShareUrlDomain.of(
            url = uniqueUrl,
            eventName = request.eventName,
            neighborhood = request.neighborhood,
            px = request.px,
            py = request.py,
            uuid =  uniqueId,
            )

        val savedShareUrlDomain = shareUrlRepository.save(shareUrl)

        return CreateEvent.Response(savedShareUrlDomain.url)
    }
    private fun generateUniqueUrl(request: CreateEvent.Request, uniqueId: String): String {
        return "$baseUrl/${request.eventName}/${request.neighborhood}/$uniqueId"
    }
}