package com.ping.api.global

import com.fasterxml.jackson.databind.ObjectMapper
import com.ping.infra.ping.mongo.repository.BookmarkMongoRepository
import com.ping.infra.member.mongo.repository.ProfileMongoRepository
import com.ping.infra.event.mongo.repository.SubwayMongoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.nio.charset.StandardCharsets

@AutoConfigureRestDocs
@Import(RestDocsConfig::class)
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
abstract class BaseRestDocsTest {
    @Autowired
    lateinit var resultHandler: RestDocumentationResultHandler

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookmarkMongoRepository: BookmarkMongoRepository

    @MockBean
    private lateinit var profileMongoRepository: ProfileMongoRepository

    @MockBean
    private lateinit var subwayMongoRepository: SubwayMongoRepository

    @BeforeEach
    fun mockMvcSetup(
        applicationContext: WebApplicationContext,
        contextProvider: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(contextProvider).uris()
                    .withScheme("https")
            )
            .alwaysDo<DefaultMockMvcBuilder?>(resultHandler)
            .addFilters<DefaultMockMvcBuilder?>(CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .build()
    }
}