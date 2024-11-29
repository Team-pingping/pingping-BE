package com.ping.domain.ping.aggregate

data class PlaceDomain(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)