package com.ping.infra.member.mongo.repository

import com.ping.infra.member.mongo.entity.ProfileEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface ProfileMongoRepository : MongoRepository<ProfileEntity, String> {
}