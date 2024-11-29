package com.ping.infra.ping

import com.ping.domain.ping.aggregate.NonMemberStoreUrlDomain
import com.ping.domain.ping.repository.NonMemberStoreUrlRepository
import com.ping.infra.ping.jpa.repository.NonMemberStoreUrlJpaRepository
import com.ping.infra.ping.mapper.NonMemberStoreUrlMapper
import org.springframework.stereotype.Repository

@Repository
class NonMemberStoreUrlRepositoryImpl(
    private val nonMemberStoreUrlJpaRepository: NonMemberStoreUrlJpaRepository
) : NonMemberStoreUrlRepository {
    override fun saveAll(nonMemberStoreUrlDomains: List<NonMemberStoreUrlDomain>): List<NonMemberStoreUrlDomain> {
        return nonMemberStoreUrlJpaRepository.saveAll(nonMemberStoreUrlDomains.map { NonMemberStoreUrlMapper.toEntity(it) })
            .map { NonMemberStoreUrlMapper.toDomain(it) }
    }

    override fun findAllByNonMemberId(nonMemberId: Long): List<NonMemberStoreUrlDomain> {
        return nonMemberStoreUrlJpaRepository.findAllByNonMemberId(nonMemberId)
            .map { NonMemberStoreUrlMapper.toDomain(it) }
    }

    override fun deleteAllByIds(ids: List<Long>) {
        nonMemberStoreUrlJpaRepository.deleteAllById(ids)
    }
}