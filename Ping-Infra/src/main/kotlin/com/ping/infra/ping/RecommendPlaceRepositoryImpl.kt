package com.ping.infra.ping

import com.ping.domain.ping.aggregate.RecommendPlaceDomain
import com.ping.domain.ping.repository.RecommendPlaceRepository
import com.ping.infra.ping.jpa.repository.RecommendPlaceJpaRepository
import com.ping.infra.ping.mapper.RecommendPlaceMapper
import org.springframework.stereotype.Repository

@Repository
class RecommendPlaceRepositoryImpl(
    private val recommendPlaceJpaRepository: RecommendPlaceJpaRepository
) : RecommendPlaceRepository {
    override fun saveAll(recommendPlaceDomains: List<RecommendPlaceDomain>): List<RecommendPlaceDomain> {
        return recommendPlaceJpaRepository.saveAll(recommendPlaceDomains.map { RecommendPlaceMapper.toEntity(it) })
                .map { RecommendPlaceMapper.toDomain(it) }
    }

    override fun findAllByShareUrlId(shareUrlId: Long): List<RecommendPlaceDomain> {
        return recommendPlaceJpaRepository.findAllByShareUrlId(shareUrlId)
                .map { RecommendPlaceMapper.toDomain(it) }
    }
}