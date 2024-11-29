package com.ping.api.global

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class StaticRoutingConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/swagger/**").addResourceLocations("classpath:/static/swagger/")
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/swagger/swagger-ui/")
    }
}