package com.ping.infra.nonmember.domain.jpa.entity
import com.ping.common.entity.BaseTimeEntity
import com.ping.domain.nonmember.aggregate.NonMember
import jakarta.persistence.*

@Entity(name = "non_member")
@Table
class NonMemberEntity(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_url_id", nullable = false)
    var shareUrl: ShareUrlEntity
) : BaseTimeEntity(){
    fun toDomain() : NonMember{
        return NonMember(
            id = this.id,
            name = this.name,
            password = this.password,
            shareUrl = this.shareUrl.toDomain()
        )
    }
}