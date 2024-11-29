package com.ping.infra.member

import com.ping.domain.member.aggregate.ProfileDomain
import com.ping.domain.member.repository.ProfileRepository
import com.ping.infra.member.mapper.ProfileMapper
import com.ping.infra.member.mongo.repository.ProfileMongoRepository
import org.springframework.stereotype.Repository

@Repository
class ProfileRepositoryImpl(
    private val profileMongoRepository: ProfileMongoRepository
) : ProfileRepository {
    override fun findAll(): List<ProfileDomain> {
        return profileMongoRepository.findAll().map { ProfileMapper.toDomain(it) }
    }
}