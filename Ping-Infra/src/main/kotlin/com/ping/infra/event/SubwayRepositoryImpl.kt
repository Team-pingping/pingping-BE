package com.ping.infra.event

import com.ping.domain.event.aggregate.SubwayDomain
import com.ping.domain.event.repository.SubwayRepository
import com.ping.infra.event.mapper.SubwayMapper
import com.ping.infra.event.mongo.repository.SubwayMongoRepository
import org.springframework.stereotype.Repository

@Repository
class SubwayRepositoryImpl (
    private val subwayMongoRepository: SubwayMongoRepository
) : SubwayRepository {
    override fun findByStation(station: String): SubwayDomain? {
        return subwayMongoRepository.findByStation(station)?.let { SubwayMapper.toDomain(it) }
    }
}