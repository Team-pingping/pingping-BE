package com.ping.infra.event.mongo.repository

import com.ping.infra.event.mongo.entity.SubwayEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface SubwayMongoRepository : MongoRepository<SubwayEntity, String> {
    fun findByStation(station: String): SubwayEntity?
}