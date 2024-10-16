package com.ping.infra.nonmember.domain.jpa.entity

import com.ping.common.entity.BaseTimeEntity
import com.ping.domain.nonmember.aggregate.ShareUrl
import jakarta.persistence.*

@Entity(name = "share_url")
@Table
class ShareUrlEntity(
    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "event_name", nullable = false)
    val eventName: String,

    @Column(name = "neighborhood", nullable = false)
    val neighborhood: String,

) : BaseTimeEntity() {
    fun toDomain(): ShareUrl {
       return ShareUrl(
           id = this.id,
           url = this.url,
           eventName = this.eventName,
           neighborhood = this.neighborhood
       )
    }
}