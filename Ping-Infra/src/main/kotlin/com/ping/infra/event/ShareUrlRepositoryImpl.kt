package com.ping.infra.event

import com.ping.domain.event.aggregate.ShareUrlDomain
import com.ping.domain.event.repository.ShareUrlRepository
import com.ping.infra.event.jpa.repository.ShareUrlJpaRepository
import com.ping.infra.event.mapper.ShareUrlMapper
import org.springframework.stereotype.Repository

@Repository
class ShareUrlRepositoryImpl(
    private val shareUrlJpaRepository: ShareUrlJpaRepository
) : ShareUrlRepository {
    override fun findByUuid(uuid: String): ShareUrlDomain? {
        return shareUrlJpaRepository.findByUuid(uuid)?.let {
            ShareUrlMapper.toDomain(it)
        }
    }

    override fun save(shareUrlDomain: ShareUrlDomain): ShareUrlDomain {
        return ShareUrlMapper.toDomain(shareUrlJpaRepository.save(ShareUrlMapper.toEntity(shareUrlDomain)))
    }
}