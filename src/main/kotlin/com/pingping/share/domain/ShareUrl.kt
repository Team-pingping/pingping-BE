package com.pingping.share.domain

import com.pingping.global.entity.BaseTimeEntity
import com.pingping.member.domain.NonMember
import jakarta.persistence.*

@Entity
@Table(name = "share_url")
class ShareUrl(
    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "event_name", nullable = false)
    val eventName: String,

    @Column(name = "neighborhood", nullable = false)
    val neighborhood: String,

    @OneToMany(mappedBy = "shareUrl", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val nonMembers: MutableList<NonMember> = mutableListOf()
) : BaseTimeEntity() {

    fun addNonMember(nonMember: NonMember) {
        nonMembers.add(nonMember)
        nonMember.shareUrl = this
    }
}