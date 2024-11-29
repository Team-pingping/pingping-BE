package com.ping.domain.ping.repository

import com.ping.domain.ping.aggregate.BookmarkDomain

interface BookmarkRepository {
    fun saveAll(bookmarkDomains: List<BookmarkDomain>) : List<BookmarkDomain>
    fun findAllBySidIn(sids: List<String>) : List<BookmarkDomain>
    fun findAllByLocationNear(px: Double, py: Double, distance: Double): List<BookmarkDomain>
    fun existsBySid(sid: String): Boolean
}