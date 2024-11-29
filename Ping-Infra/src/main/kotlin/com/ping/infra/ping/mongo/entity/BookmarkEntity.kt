package com.ping.infra.ping.mongo.entity

import com.ping.infra.config.mongo.BaseTimeMongoEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "bookmark")
data class BookmarkEntity(
    val name: String,
    val px: Double,
    val py: Double,
    @Id
    @Indexed(unique = true)
    val sid: String,
    val address: String,
    val mcidName: String,
    val url: String,
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    val location: GeoJsonPoint = GeoJsonPoint(px, py),
): BaseTimeMongoEntity()