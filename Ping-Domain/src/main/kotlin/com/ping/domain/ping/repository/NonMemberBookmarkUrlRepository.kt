package com.ping.domain.ping.repository

import com.ping.domain.ping.aggregate.NonMemberBookmarkUrlDomain

interface NonMemberBookmarkUrlRepository {
    fun saveAll(nonMemberBookmarkUrlDomains: List<NonMemberBookmarkUrlDomain>) : List<NonMemberBookmarkUrlDomain>
    fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberBookmarkUrlDomain>
    fun deleteAllByIds(ids: List<Long>)
}