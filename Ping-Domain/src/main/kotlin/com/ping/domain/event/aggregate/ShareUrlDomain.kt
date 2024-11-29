package com.ping.domain.event.aggregate

import java.time.LocalDateTime

data class ShareUrlDomain(
    val id: Long,
    val url: String,
    val eventName: String,
    val neighborhood: String,
    val px: Double,
    val py: Double,
    val uuid: String,
    val pingUpdateTime: LocalDateTime?,
){
    companion object {
        fun of(url: String, eventName: String, neighborhood: String, px: Double, py: Double, uuid: String,) =
            ShareUrlDomain(0L, url, eventName, neighborhood, px, py, uuid, null)
    }
}