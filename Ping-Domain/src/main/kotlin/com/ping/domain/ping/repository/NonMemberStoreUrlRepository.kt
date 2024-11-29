package com.ping.domain.ping.repository

import com.ping.domain.ping.aggregate.NonMemberStoreUrlDomain

interface NonMemberStoreUrlRepository {
    fun saveAll(nonMemberStoreUrlDomains: List<NonMemberStoreUrlDomain>) : List<NonMemberStoreUrlDomain>
    fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberStoreUrlDomain>
    fun deleteAllByIds(ids: List<Long>)
}