package com.pingping.member.domain

import com.pingping.global.entity.BaseTimeEntity
import com.pingping.share.domain.ShareUrl
import jakarta.persistence.*

@Entity
@Table(name = "non_member")
class NonMember(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_url_id", nullable = false)
    var shareUrl: ShareUrl? = null
) : BaseTimeEntity()