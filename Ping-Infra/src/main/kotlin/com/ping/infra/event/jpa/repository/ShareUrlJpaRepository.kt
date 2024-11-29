package com.ping.infra.event.jpa.repository

import com.ping.infra.event.jpa.entity.ShareUrlEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ShareUrlJpaRepository : JpaRepository<ShareUrlEntity, Long>{
    fun findByUuid(uuid: String): ShareUrlEntity?
}