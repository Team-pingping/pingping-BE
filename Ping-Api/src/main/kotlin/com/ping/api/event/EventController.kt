package com.ping.api.event

import com.ping.application.event.service.EventService
import com.ping.application.event.dto.CreateEvent
import com.ping.common.exception.SuccessResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController(
    private val eventService: EventService,
) {
    @PostMapping(EventApi.BASE_URL)
    fun create(@RequestBody request: CreateEvent.Request): ResponseEntity<SuccessResponse<CreateEvent.Response>> {
        val response = eventService.create(request)
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "공유 URL이 성공적으로 생성되었습니다.", response))
    }
}