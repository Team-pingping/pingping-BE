package com.ping.infra.ping

import com.ping.domain.ping.aggregate.NonMemberBookmarkUrlDomain
import com.ping.domain.ping.repository.NonMemberBookmarkUrlRepository
import com.ping.infra.ping.jpa.repository.NonMemberBookmarkUrlJpaRepository
import com.ping.infra.ping.mapper.NonMemberBookmarkUrlMapper
import org.springframework.stereotype.Repository

@Repository
class NonMemberBookmarkUrlRepositoryImpl(
    private val nonMemberBookmarkUrlJpaRepository: NonMemberBookmarkUrlJpaRepository
) : NonMemberBookmarkUrlRepository {
    override fun saveAll(nonMemberBookmarkUrlDomains: List<NonMemberBookmarkUrlDomain>): List<NonMemberBookmarkUrlDomain> {
        return nonMemberBookmarkUrlJpaRepository.saveAll(nonMemberBookmarkUrlDomains.map {
            NonMemberBookmarkUrlMapper.toEntity(it)
        }).map { NonMemberBookmarkUrlMapper.toDomain(it) }
    }

    override fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberBookmarkUrlDomain> {
        return nonMemberBookmarkUrlJpaRepository.findAllByNonMemberId(nonMemberId)
            .map { NonMemberBookmarkUrlMapper.toDomain(it) }
    }

    override fun deleteAllByIds(ids: List<Long>) {
        nonMemberBookmarkUrlJpaRepository.deleteAllById(ids)
    }
}