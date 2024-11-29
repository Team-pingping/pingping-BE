package com.ping.domain.ping.aggregate

import com.ping.domain.member.aggregate.NonMemberDomain

data class NonMemberStoreUrlDomain(
    val id: Long,
    val nonMember: NonMemberDomain,
    val storeUrl: String
) {
    companion object {
        fun of(nonMemberDomain: NonMemberDomain, storeUrl: List<String>) =
            storeUrl.map { NonMemberStoreUrlDomain(0L, nonMemberDomain, it) }
    }
}