package com.ping.infra.ping.jpa.entity

import com.ping.infra.config.jpa.BaseTimeEntity
import com.ping.infra.event.jpa.entity.ShareUrlEntity
import jakarta.persistence.*

@Entity(name = "recommend_place")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["share_url_id", "sid"])])
class RecommendPlaceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val shareUrl: ShareUrlEntity,

    @Column(nullable = false)
    val sid: String,
) : BaseTimeEntity()