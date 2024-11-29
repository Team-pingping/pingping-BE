package com.ping.infra.ping.jpa.repository

import com.ping.infra.ping.jpa.entity.NonMemberBookmarkUrlEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NonMemberBookmarkUrlJpaRepository: JpaRepository<NonMemberBookmarkUrlEntity, Long>{
    fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberBookmarkUrlEntity>
}