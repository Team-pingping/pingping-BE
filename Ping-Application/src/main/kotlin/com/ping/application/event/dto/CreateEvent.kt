package com.ping.application.event.dto

interface CreateEvent {
    data class Request(
        val neighborhood: String,
        val px: Double,
        val py: Double,
        val eventName: String,
    )
    data class Response(
        val shareUrl: String,
    )
}