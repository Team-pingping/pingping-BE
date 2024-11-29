package com.ping.domain.member.repository

import com.ping.domain.member.aggregate.ProfileDomain

interface ProfileRepository {
    fun findAll(): List<ProfileDomain>
}