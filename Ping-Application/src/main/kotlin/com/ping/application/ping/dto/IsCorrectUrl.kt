package com.ping.application.ping.dto

interface IsCorrectUrl {
    data class Request(
        val url: String,
    )
}