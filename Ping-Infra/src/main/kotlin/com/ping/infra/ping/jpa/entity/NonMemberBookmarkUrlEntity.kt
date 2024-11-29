package com.ping.infra.ping.jpa.entity

import com.ping.infra.config.jpa.BaseTimeEntity
import com.ping.infra.member.jpa.entity.NonMemberEntity
import jakarta.persistence.*

@Entity(name = "non_member_bookmark_url")
class NonMemberBookmarkUrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val nonMember: NonMemberEntity,

    @Column(nullable = false)
    val bookmarkUrl: String
) : BaseTimeEntity()