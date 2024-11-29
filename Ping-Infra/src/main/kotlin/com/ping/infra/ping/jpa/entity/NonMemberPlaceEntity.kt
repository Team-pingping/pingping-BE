package com.ping.infra.ping.jpa.entity

import com.ping.common.enums.PlaceType
import com.ping.infra.config.jpa.BaseTimeEntity
import com.ping.infra.member.jpa.entity.NonMemberEntity
import jakarta.persistence.*

@Entity(name = "non_member_place")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["non_member_id", "sid"])])
class NonMemberPlaceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val nonMember: NonMemberEntity,

    @Column(nullable = false)
    val sid: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val placeType: PlaceType,
) : BaseTimeEntity()