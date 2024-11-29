package com.ping.domain.event.repository

import com.ping.domain.event.aggregate.ShareUrlDomain

interface ShareUrlRepository {
    fun findByUuid(uuid: String): ShareUrlDomain?
    fun save(shareUrlDomain: ShareUrlDomain): ShareUrlDomain
}
