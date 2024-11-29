package com.ping.infra.member

import com.ping.domain.member.aggregate.NonMemberDomain
import com.ping.domain.member.repository.NonMemberRepository
import com.ping.infra.member.jpa.repository.NonMemberJpaRepository
import com.ping.infra.member.mapper.NonMemberMapper
import org.springframework.stereotype.Repository

@Repository
class NonMemberRepositoryImpl(
    private val nonMemberJpaRepository: NonMemberJpaRepository,
) : NonMemberRepository {
    override fun findByShareUrlIdAndName(shareUrlId: Long, name: String): NonMemberDomain? {
        return nonMemberJpaRepository.findByShareUrlIdAndName(shareUrlId, name)?.let {
            NonMemberMapper.toDomain(it)
        }
    }

    override fun save(nonMemberDomain: NonMemberDomain): NonMemberDomain {
        return NonMemberMapper.toDomain(nonMemberJpaRepository.save(NonMemberMapper.toEntity(nonMemberDomain)))
    }

    override fun findAllByShareUrl(shareUrlId: Long): List<NonMemberDomain> {
        return nonMemberJpaRepository.findAllByShareUrlId(shareUrlId).map { NonMemberMapper.toDomain(it) }
    }

    override fun findById(nonMemberId: Long): NonMemberDomain? {
        return nonMemberJpaRepository.findById(nonMemberId).get().let {
            NonMemberMapper.toDomain(it)
        }
    }

    override fun findAllBySidAndShareUrlId(sid: String, sharedUrlId: Long): List<NonMemberDomain> {
        return nonMemberJpaRepository.findAllBySidAndShareUrlId(sid, sharedUrlId).map { NonMemberMapper.toDomain(it) }
    }
}