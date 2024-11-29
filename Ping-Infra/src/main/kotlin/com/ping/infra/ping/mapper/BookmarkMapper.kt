package com.ping.infra.ping.mapper

import com.ping.domain.ping.aggregate.BookmarkDomain
import com.ping.infra.ping.mongo.entity.BookmarkEntity
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

object BookmarkMapper {

    fun toDomain(bookmarkEntity: BookmarkEntity) = BookmarkDomain(
        bookmarkEntity.name,
        bookmarkEntity.px,
        bookmarkEntity.py,
        bookmarkEntity.sid,
        bookmarkEntity.address,
        bookmarkEntity.mcidName,
        bookmarkEntity.url,
    )

    fun toEntity(bookmarkDomain: BookmarkDomain) = BookmarkEntity(
        bookmarkDomain.name,
        bookmarkDomain.px,
        bookmarkDomain.py,
        bookmarkDomain.sid,
        bookmarkDomain.address,
        bookmarkDomain.mcidName,
        bookmarkDomain.url,
        GeoJsonPoint(bookmarkDomain.px, bookmarkDomain.py),
    )
}