package com.pingping.global.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("핑핑이들의 API")
            .version("V1.0.0")
            .description("핑핑이들의 API입니다.")

        return OpenAPI()
            .info(info)
    }
}