package com.ping.application.event.dto

interface SearchPlace {
    data class Response(
        val name: String,
        val address: String,
        val px: Double,
        val py: Double,
    )
}