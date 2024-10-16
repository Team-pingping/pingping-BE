package com.ping.infra.nonmember.domain.repositoryImpl

import com.ping.domain.nonmember.aggregate.ShareUrl
import com.ping.domain.nonmember.repository.ShareUrlRepository
import com.ping.infra.nonmember.domain.jpa.repository.ShareUrlJpaRepository
import com.ping.infra.nonmember.domain.mapper.ShareUrlMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ShareUrlRepositoryImpl(
    private val shareUrlJpaRepository: ShareUrlJpaRepository
) : ShareUrlRepository {
    override fun findById(id: Long): ShareUrl? {
        return shareUrlJpaRepository.findByIdOrNull(id)?.let {
            ShareUrlMapper.toDomain(it)
        }
    }
}