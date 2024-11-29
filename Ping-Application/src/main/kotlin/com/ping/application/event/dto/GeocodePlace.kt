package com.ping.application.event.dto

interface GeocodePlace {
    data class Request(
        val address: String,
    )

    data class Response(
        val address: String,
        val px: Double?,
        val py: Double?,
    )
}