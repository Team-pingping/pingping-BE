package com.ping.infra.event.jpa.entity

import com.ping.infra.config.jpa.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "share_url")
class ShareUrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val url: String,

    @Column(nullable = false)
    val eventName: String,

    @Column(nullable = false)
    val neighborhood: String,

    @Column(nullable = false)
    val px: Double,

    @Column(nullable = false)
    val py: Double,

    @Column(nullable = false, unique = true)
    val uuid: String,

    val pingUpdateTime: LocalDateTime?

) : BaseTimeEntity()