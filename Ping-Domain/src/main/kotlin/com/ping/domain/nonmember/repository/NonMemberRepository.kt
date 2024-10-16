package com.ping.domain.nonmember.repository

import com.ping.domain.nonmember.aggregate.NonMember

interface NonMemberRepository {
    fun findByShareUrlIdAndName(shareUrlId: Long, name: String) : NonMember?

    fun save(nonMember: NonMember): NonMember
}