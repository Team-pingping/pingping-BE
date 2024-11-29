package com.ping.application.event.dto

interface SavePlace {
    data class Request (
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
    )
}