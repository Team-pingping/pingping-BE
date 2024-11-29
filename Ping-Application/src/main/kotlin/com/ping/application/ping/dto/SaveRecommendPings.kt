package com.ping.application.ping.dto

interface SaveRecommendPings {
    data class Request(
        val uuid: String,
        val sids: List<String>,
    )
}