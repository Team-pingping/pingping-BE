package com.ping.infra.nonmember.domain.mapper

import com.ping.domain.nonmember.aggregate.NonMember
import com.ping.infra.nonmember.domain.jpa.entity.NonMemberEntity

class NonMemberMapper {
    companion object {

        fun toDomain(nonMemberEntity: NonMemberEntity): NonMember {
            return NonMember(
                nonMemberEntity.id,
                nonMemberEntity.name,
                nonMemberEntity.password,
                nonMemberEntity.shareUrl.toDomain()
            )
        }

        fun toEntity(nonMember: NonMember): NonMemberEntity {
            return NonMemberEntity(
                nonMember.name,
                nonMember.password,
                ShareUrlMapper.toEntity(nonMember.shareUrl)
            )
        }
    }
}