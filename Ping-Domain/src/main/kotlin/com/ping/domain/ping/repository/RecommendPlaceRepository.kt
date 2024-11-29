package com.ping.domain.ping.repository

import com.ping.domain.ping.aggregate.RecommendPlaceDomain

interface RecommendPlaceRepository {
    fun saveAll(recommendPlaceDomains: List<RecommendPlaceDomain>): List<RecommendPlaceDomain>

    fun findAllByShareUrlId(shareUrlId: Long): List<RecommendPlaceDomain>
}