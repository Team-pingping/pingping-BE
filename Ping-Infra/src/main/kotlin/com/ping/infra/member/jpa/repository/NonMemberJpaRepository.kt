package com.ping.infra.member.jpa.repository

import com.ping.infra.member.jpa.entity.NonMemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NonMemberJpaRepository : JpaRepository<NonMemberEntity, Long> {
    fun findByShareUrlIdAndName(urlId: Long, name: String): NonMemberEntity?

    fun findAllByShareUrlId(shareUrlId: Long): List<NonMemberEntity>

    @Query("""
        select n
        from non_member n
        join non_member_place pc on n.id = pc.nonMember.id
        where n.shareUrl.id = :shareUrlId and pc.sid=:sid
    """)
    fun findAllBySidAndShareUrlId(sid: String, shareUrlId: Long): List<NonMemberEntity>
}