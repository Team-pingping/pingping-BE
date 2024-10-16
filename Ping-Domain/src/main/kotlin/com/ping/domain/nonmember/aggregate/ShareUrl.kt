package com.ping.domain.nonmember.aggregate

data class ShareUrl(
    val id: Long,
    val url: String,
    val eventName: String,
    val neighborhood: String
)