package com.ping.infra.ping.mongo.repository

import com.ping.infra.ping.mongo.entity.BookmarkEntity
import org.springframework.data.geo.Distance
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.repository.MongoRepository

interface BookmarkMongoRepository : MongoRepository<BookmarkEntity, String> {
    fun findAllBySidIn(sids: List<String>) : List<BookmarkEntity>
    fun findAllByLocationNear(location: GeoJsonPoint, distance: Distance): List<BookmarkEntity>
    fun existsBySid(sid: String): Boolean
}