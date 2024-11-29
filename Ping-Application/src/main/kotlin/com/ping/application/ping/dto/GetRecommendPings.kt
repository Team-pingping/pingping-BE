package com.ping.application.ping.dto

interface GetRecommendPings {
    data class Response(
        val recommendPings: List<RecommendPing>,
    )

    data class RecommendPing(
        val iconLevel: Int,
        val sid: String,
        val placeName: String,
        val url: String,
        val px: Double,
        val py: Double,
        val type: String,
    )
}