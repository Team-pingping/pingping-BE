package com.ping.common.util

object AlternativeQueryProvider {
    private val alternativeQueries = mapOf(
        "을지로" to listOf("을지로3가", "을지로4가", "을지로입구")
    )

    fun getQueries(keyword: String): List<String> {
        return alternativeQueries[keyword] ?: listOf(keyword)
    }
}