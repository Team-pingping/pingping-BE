package com.ping.infra.member.jpa.entity

import com.ping.infra.config.jpa.BaseTimeEntity
import com.ping.infra.event.jpa.entity.ShareUrlEntity
import jakarta.persistence.*

@Entity(name = "non_member")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "share_url_id"])])
class NonMemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val profileSvg: String,

    @Column(nullable = false)
    val profileLockSvg: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val shareUrl: ShareUrlEntity
) : BaseTimeEntity()