package com.ping.infra.nonmember.domain.jpa.repository

import com.ping.infra.nonmember.domain.jpa.entity.ShareUrlEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ShareUrlJpaRepository : JpaRepository<ShareUrlEntity, Long>