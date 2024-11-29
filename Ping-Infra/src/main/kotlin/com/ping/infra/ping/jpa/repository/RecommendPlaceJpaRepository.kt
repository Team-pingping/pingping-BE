package com.ping.infra.ping.jpa.repository

import com.ping.infra.ping.jpa.entity.RecommendPlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RecommendPlaceJpaRepository : JpaRepository<RecommendPlaceEntity, Long> {
    fun findAllByShareUrlId(shareUrlId: Long): List<RecommendPlaceEntity>
}