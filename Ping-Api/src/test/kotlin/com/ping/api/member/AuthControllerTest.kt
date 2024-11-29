package com.ping.api.member

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ping.api.global.BaseRestDocsTest
import com.ping.application.member.dto.TokenResponse
import com.ping.application.member.service.AuthService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthController::class)
class AuthControllerTest : BaseRestDocsTest() {

    @MockBean
    private lateinit var authService: AuthService

    private val tag = "인증"

    @Test
    @DisplayName("토큰 재발급")
    fun reissueToken() {
        // given
        val tokenResponse = TokenResponse(
            accessToken = "newAccessToken123"
        )

        given(authService.reissueToken(any())).willReturn(tokenResponse)

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(AuthApi.REISSUE)
                .header("Authorization", "Bearer expiredAccessToken123")
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // REST Docs
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("새로운 액세스 토큰")
                    )
                )
            )
            .andDo( // Swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "토큰 재발급",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("만료된 액세스 토큰을 갱신하기 위한 API")
                        .requestHeaders(
                            headerWithName("Authorization").description("Access Token")
                        )
                        .responseFields(
                            fieldWithPath("accessToken").description("새로운 액세스 토큰")
                        )
                )
            )
    }
}