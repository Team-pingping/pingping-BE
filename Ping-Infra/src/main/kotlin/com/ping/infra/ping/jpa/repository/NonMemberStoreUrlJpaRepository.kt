package com.ping.infra.ping.jpa.repository

import com.ping.infra.ping.jpa.entity.NonMemberStoreUrlEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NonMemberStoreUrlJpaRepository : JpaRepository<NonMemberStoreUrlEntity, Long> {
    fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberStoreUrlEntity>
}