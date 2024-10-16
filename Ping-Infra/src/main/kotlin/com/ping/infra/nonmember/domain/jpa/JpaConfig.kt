package com.ping.infra.nonmember.domain.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.ping.infra"])
@EnableJpaRepositories(basePackages = ["com.ping.infra"])
class JpaConfig