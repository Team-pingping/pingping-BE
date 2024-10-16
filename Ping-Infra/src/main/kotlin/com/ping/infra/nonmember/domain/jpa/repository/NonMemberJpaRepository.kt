package com.ping.infra.nonmember.domain.jpa.repository

import com.ping.infra.nonmember.domain.jpa.entity.NonMemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NonMemberJpaRepository : JpaRepository<NonMemberEntity, Long> {
    fun findByShareUrlIdAndName(urlId: Long, name: String): NonMemberEntity?
}