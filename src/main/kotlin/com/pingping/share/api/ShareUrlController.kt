package com.pingping.share.api

import com.pingping.global.common.CommonResponse
import com.pingping.share.application.ShareUrlService
import com.pingping.share.dto.request.ShareUrlRequest
import com.pingping.share.dto.response.ShareUrlResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/share-url")
class ShareUrlController(
    private val shareUrlService: ShareUrlService
) {
    @PostMapping
    fun createShareUrl(
        @RequestBody request: ShareUrlRequest
    ): ResponseEntity<CommonResponse<ShareUrlResponse>>{
        val response = shareUrlService.createShareUrl(request)

        return ResponseEntity.ok(
            CommonResponse.of(status = HttpStatus.OK, message = "공유 URL이 성공적으로 생성되었습니다.", data = response)
        )
    }

}