package com.ping.api.member

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ping.api.global.BaseRestDocsTest
import com.ping.application.member.dto.GetNonMemberProfile
import com.ping.application.member.dto.LoginNonMember
import com.ping.application.member.dto.LoginTokenNonMember
import com.ping.application.member.service.NonMemberService
import com.ping.application.ping.dto.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(NonMemberController::class)
class NonMemberControllerTest : BaseRestDocsTest() {

    @MockBean
    private lateinit var nonMemberService: NonMemberService

    private val tag = "비회원"

    @Test
    @DisplayName("비회원 로그인")
    fun loginNonMember() {
        // given
        val request = LoginNonMember.Request(nonMemberId = 1L, password = "1234")
        val response = LoginNonMember.Response(
            nonMemberId = 1L,
            name = "테스트 사용자",
            bookmarkUrls = listOf("bookmarkUrl1", "bookmarkUrl2"),
            storeUrls = listOf("storeUrl1")
        )

        given(nonMemberService.login(request)).willReturn(response)

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.put(NonMemberApi.LOGIN)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("nonMemberId").description("비회원 ID"),
                        fieldWithPath("password").description("비회원 비밀번호")
                    ),
                    responseFields(
                        fieldWithPath("nonMemberId").description("로그인한 비회원의 ID"),
                        fieldWithPath("name").description("비회원의 이름"),
                        fieldWithPath("bookmarkUrls").description("비회원의 북마크 URL 목록"),
                        fieldWithPath("storeUrls").description("비회원의 스토어 URL 목록")
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 로그인",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원 로그인")
                        .requestFields(
                            fieldWithPath("nonMemberId").description("비회원 ID"),
                            fieldWithPath("password").description("비회원 비밀번호")
                        )
                        .responseFields(
                            fieldWithPath("nonMemberId").description("로그인한 비회원의 ID"),
                            fieldWithPath("name").description("비회원의 이름"),
                            fieldWithPath("bookmarkUrls").description("비회원의 북마크 URL 목록"),
                            fieldWithPath("storeUrls").description("비회원의 스토어 URL 목록")
                        )
                )
            )
    }

    @Test
    @DisplayName("비회원 로그인 (토큰 발급)")
    fun loginNonMemberWithToken() {
        // given
        val request = LoginNonMember.Request(nonMemberId = 1L, password = "1234")
        val response = LoginTokenNonMember.Response(
            nonMemberId = 1L,
            name = "테스트 사용자",
            accessToken = "abc123"
        )

        given(nonMemberService.loginWithToken(request)).willReturn(response)

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(NonMemberApi.LOGIN_TOKEN)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // REST Docs 생성
                resultHandler.document(
                    requestFields(
                        fieldWithPath("nonMemberId").description("비회원 ID"),
                        fieldWithPath("password").description("비회원 비밀번호")
                    ),
                    responseFields(
                        fieldWithPath("nonMemberId").description("로그인한 비회원의 ID"),
                        fieldWithPath("name").description("비회원의 이름"),
                        fieldWithPath("accessToken").description("로그인 후 발급된 Access Token")
                    )
                )
            )
            .andDo( // Swagger 문서화
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 로그인 (토큰 발급)",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원이 ID와 비밀번호를 사용하여 로그인하고 Access Token을 발급받는 API")
                        .requestFields(
                            fieldWithPath("nonMemberId").description("비회원 ID"),
                            fieldWithPath("password").description("비회원 비밀번호")
                        )
                        .responseFields(
                            fieldWithPath("nonMemberId").description("로그인한 비회원의 ID"),
                            fieldWithPath("name").description("비회원의 이름"),
                            fieldWithPath("accessToken").description("로그인 후 발급된 Access Token")
                        )
                )
            )
    }

    @Test
    @DisplayName("비회원 프로필 불러오기")
    fun getNonMemberProfile() {
        // given
        val nonMemberId = 1L
        val response = GetNonMemberProfile.Response(
            name = "쏘야",
            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg",
            profileLockSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1-lock.svg"
        )
        given(nonMemberService.getNonMemberProfile(nonMemberId)).willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get(NonMemberApi.NONMEMBER_ID, nonMemberId)
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    responseFields(
                        fieldWithPath("name").description("비회원 이름"),
                        fieldWithPath("profileSvg").description("프로필 svg"),
                        fieldWithPath("profileLockSvg").description("프로필 잠금 svg"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 프로필 불러오기",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원 프로필 불러오기")
                        .responseFields(
                            fieldWithPath("name").description("비회원 이름"),
                            fieldWithPath("profileSvg").description("프로필 svg"),
                            fieldWithPath("profileLockSvg").description("프로필 잠금 svg"),
                        )
                )
            )
    }

}