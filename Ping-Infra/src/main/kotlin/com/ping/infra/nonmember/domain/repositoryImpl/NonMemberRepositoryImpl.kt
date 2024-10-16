package com.ping.infra.nonmember.domain.repositoryImpl

import com.ping.domain.nonmember.aggregate.NonMember
import com.ping.domain.nonmember.repository.NonMemberRepository
import com.ping.infra.nonmember.domain.jpa.repository.NonMemberJpaRepository
import com.ping.infra.nonmember.domain.mapper.NonMemberMapper
import org.springframework.stereotype.Repository

@Repository
class NonMemberRepositoryImpl(
    private val nonMemberJpaRepository: NonMemberJpaRepository,
) : NonMemberRepository {
    override fun findByShareUrlIdAndName(shareUrlId: Long, name: String): NonMember? {
        return nonMemberJpaRepository.findByShareUrlIdAndName(shareUrlId, name)?.let {
            NonMemberMapper.toDomain(it)
        }
    }

    override fun save(nonMember: NonMember): NonMember {
        val nonMemberEntity = NonMemberMapper.toEntity(nonMember)
        return NonMemberMapper.toDomain(nonMemberEntity)
    }
}