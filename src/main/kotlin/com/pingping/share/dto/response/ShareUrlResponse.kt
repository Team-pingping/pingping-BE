package com.pingping.share.dto.response

data class ShareUrlResponse (
    val shareUrlId: Long,
    val shareUrl: String,
    val eventName: String,
    val neighborhood: String
)