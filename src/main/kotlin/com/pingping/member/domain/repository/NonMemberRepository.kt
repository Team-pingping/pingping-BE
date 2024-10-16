package com.pingping.member.domain.repository

import com.pingping.member.domain.NonMember
import org.springframework.data.jpa.repository.JpaRepository

interface NonMemberRepository : JpaRepository<NonMember, Long> {
    fun findByShareUrlIdAndName(urlId: Long, name: String): NonMember?
}