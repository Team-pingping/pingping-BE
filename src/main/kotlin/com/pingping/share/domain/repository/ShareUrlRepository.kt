package com.pingping.share.domain.repository

import com.pingping.share.domain.ShareUrl
import org.springframework.data.jpa.repository.JpaRepository

interface ShareUrlRepository : JpaRepository<ShareUrl, Long>