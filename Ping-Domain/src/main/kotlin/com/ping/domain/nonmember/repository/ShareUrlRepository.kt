package com.ping.domain.nonmember.repository

import com.ping.domain.nonmember.aggregate.ShareUrl

interface ShareUrlRepository {
    fun findById(id: Long): ShareUrl?
}