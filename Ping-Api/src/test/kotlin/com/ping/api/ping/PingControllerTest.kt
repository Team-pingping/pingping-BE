package com.ping.api.ping

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ping.api.global.BaseRestDocsTest
import com.ping.application.member.dto.CreateNonMember
import com.ping.application.ping.dto.*
import com.ping.application.ping.service.PingService
import com.ping.domain.ping.service.PingUrlService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PingController::class)
class PingControllerTest : BaseRestDocsTest() {

    @MockBean
    private lateinit var pingUrlService: PingUrlService

    @MockBean
    private lateinit var pingService: PingService

    private val tag = "핑"

    @Test
    @DisplayName("유효한 북마크 링크")
    fun isCorrectBookmarkUrl() {
        // given
        val request = IsCorrectUrl.Request("https://naver.me/Fqimcb8B")

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.put(PingApi.BOOKMARK)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("url").description("북마크 url"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "유효한 북마크 링크",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("유효한 북마크 링크")
                        .requestFields(
                            fieldWithPath("url").description("북마크 url")
                        )
                )
            )
    }

    @Test
    @DisplayName("유효한 가게 링크")
    fun isCorrectStoreUrl() {
        // given
        val request = IsCorrectUrl.Request("https://naver.me/xecfaJTy")

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.put(PingApi.STORE)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("url").description("가게 url"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "유효한 가게",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("유효한 가게 ")
                        .requestFields(
                            fieldWithPath("url").description("가게 url")
                        )
                )
            )
    }

    @Test
    @DisplayName("회원 핑 저장")
    fun saveMemberPing() {
        // given
        val request = MemberPing.Request(
            sid = "testSid"
        )
        val jsonRequest = """
            {
                "sid": "${request.sid}"
            }
        """.trimIndent()

        doNothing().`when`(pingService).saveMemberPing(any(), any())

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(PingApi.PING_MEMBER)
                .header("Authorization", "Bearer validAccessToken123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // REST Docs
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    requestFields(
                        fieldWithPath("sid").description("장소 ID")
                    )
                )
            )
            .andDo( // Swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "멤버 핑 저장",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("멤버의 핑을 저장하는 API")
                        .requestHeaders(
                            headerWithName("Authorization").description("Access Token")
                        )
                        .requestFields(
                            fieldWithPath("sid").description("장소 ID")
                        )
                )
            )
    }

    @Test
    @DisplayName("비회원 핑 생성")
    fun createNonMemberPings() {
        // given
        val request = CreateNonMember.Request(
            uuid = "test",
            name = "윤소민",
            password = "1234",
            bookmarkUrls = listOf("https://naver.me/Fqimcb8B", "https://naver.me/xUwGH5c3"),
            storeUrls = listOf("https://naver.me/GuGEom4T", "https://naver.me/FuVzL1bq")
        )

        //when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(PingApi.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("uuid").description("공유 url 뒤에 붙는 uuid"),
                        fieldWithPath("name").description("비회원 이름"),
                        fieldWithPath("password").description("비회원 비밀번호"),
                        fieldWithPath("bookmarkUrls").description("네이버 북마크 링크 리스트"),
                        fieldWithPath("storeUrls").description("네이버 개별지도 링크 리스트"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 핑 생성",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원 핑 생성")
                        .requestFields(
                            fieldWithPath("uuid").description("공유 url 뒤에 붙는 uuid"),
                            fieldWithPath("name").description("비회원 이름"),
                            fieldWithPath("password").description("비회원 비밀번호"),
                            fieldWithPath("bookmarkUrls").description("네이버 북마크 링크 리스트"),
                            fieldWithPath("storeUrls").description("네이버 개별지도 링크 리스트"),
                        )
                )
            )
    }

    @Test
    @DisplayName("추천 핑 저장")
    fun saveRecommendPings() {
        // given
        val request = SaveRecommendPings.Request(
            uuid = "test",
            sids = listOf(
                "1445446311",
                "1250904288",
            )
        )

        val response = GetAllNonMemberPings.Response(
            eventName = "핑핑이들 여행",
            neighborhood = "강원도 속초",
            px = 127.00001,
            py = 37.00001,
            pingLastUpdateTime = "16분",
            recommendPings = listOf(
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1445446311",
                    placeName = "꾸아 광교점",
                    url = "https://map.naver.com/p/entry/place/1445446311",
                    px = 127.0548454,
                    py = 37.2938313,
                    type = "음식점",
                ),
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1250904288",
                    placeName = "옴레스토랑 광교점",
                    url = "https://map.naver.com/p/entry/place/1250904288",
                    px = 127.0505683,
                    py = 37.2903113,
                    type = "음식점",
                )
            ),
            nonMembers = listOf(
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 1,
                    name = "핑핑이1",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                ),
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 2,
                    name = "핑핑이2",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                )
            ),
            pings = listOf(
                GetAllNonMemberPings.Ping(
                    iconLevel = 2,
                    sid = "1445446311",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 1,
                            name = "핑핑이1",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        ),
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 2,
                            name = "핑핑이2",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        )
                    ),
                    url = "https://map.naver.com/p/entry/place/1946678040",
                    placeName = "호이",
                    px = 126.971178,
                    py = 37.5302481,
                    type = "음식점"
                ),
                GetAllNonMemberPings.Ping(
                    iconLevel = 1,
                    sid = "1445446311",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 1,
                            name = "핑핑이1",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        ),
                    ),
                    url = "https://map.naver.com/p/entry/place/1492901893",
                    placeName = "퍼즈앤스틸",
                    px = 126.9713426,
                    py = 37.5303303,
                    type = "음식점"
                )
            )
        )

        BDDMockito.given(pingService.saveRecommendPings(request)).willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post(PingApi.PING_RECOMMEND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("uuid").description("이벤트 식별자 UUID"),
                        fieldWithPath("sids").description("추천 장소 ID 리스트"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("eventName").description("이벤트 이름"),
                        fieldWithPath("neighborhood").description("추천 지역 이름"),
                        fieldWithPath("px").description("중심 경도"),
                        fieldWithPath("py").description("중심 위도"),
                        fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                        fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                        fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                        fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                        fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                        fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                        fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                        fieldWithPath("recommendPings[].type").description("업종"),
                        fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                        fieldWithPath("nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                        fieldWithPath("pings[].sid").description("장소 sid"),
                        fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                        fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].url").description("장소 url"),
                        fieldWithPath("pings[].placeName").description("장소 이름"),
                        fieldWithPath("pings[].px").description("경도"),
                        fieldWithPath("pings[].py").description("위도"),
                        fieldWithPath("pings[].type").description("업종"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "추천 핑 저장",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("추천 핑 저장")
                        .requestFields(
                            fieldWithPath("uuid").description("이벤트 식별자 UUID"),
                            fieldWithPath("sids").description("추천 장소 ID 리스트"),
                        )
                        .responseFields(
                            fieldWithPath("eventName").description("이벤트 이름"),
                            fieldWithPath("neighborhood").description("추천 지역 이름"),
                            fieldWithPath("px").description("중심 경도"),
                            fieldWithPath("py").description("중심 위도"),
                            fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                            fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                            fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                            fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                            fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                            fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                            fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                            fieldWithPath("recommendPings[].type").description("업종"),
                            fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                            fieldWithPath("nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                            fieldWithPath("pings[].sid").description("장소 sid"),
                            fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                            fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].url").description("장소 url"),
                            fieldWithPath("pings[].placeName").description("장소 이름"),
                            fieldWithPath("pings[].px").description("경도"),
                            fieldWithPath("pings[].py").description("위도"),
                            fieldWithPath("pings[].type").description("업종"),
                        )
                )
            )
    }

    @Test
    @DisplayName("전체 핑 불러오기")
    fun getAllNonMemberPings() {
        // given
        val uuid = "test"
        val response = GetAllNonMemberPings.Response(
            eventName = "핑핑이들 여행",
            neighborhood = "강원도 속초",
            px = 127.00001,
            py = 37.00001,
            pingLastUpdateTime = "16분",
            recommendPings = listOf(
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1445446311",
                    placeName = "꾸아 광교점",
                    url = "https://map.naver.com/p/entry/place/1445446311",
                    px = 127.0548454,
                    py = 37.2938313,
                    type = "음식점",
                ),
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1250904288",
                    placeName = "옴레스토랑 광교점",
                    url = "https://map.naver.com/p/entry/place/1250904288",
                    px = 127.0505683,
                    py = 37.2903113,
                    type = "음식점",
                )
            ),
            nonMembers = listOf(
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 1,
                    name = "핑핑이1",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                ),
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 2,
                    name = "핑핑이2",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                )
            ),
            pings = listOf(
                GetAllNonMemberPings.Ping(
                    iconLevel = 2,
                    sid = "1005227941",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 1,
                            name = "핑핑이1",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        ),
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 2,
                            name = "핑핑이2",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        )
                    ),
                    url = "https://map.naver.com/p/entry/place/1946678040",
                    placeName = "호이",
                    px = 126.971178,
                    py = 37.5302481,
                    type = "음식점"
                ),
                GetAllNonMemberPings.Ping(
                    iconLevel = 1,
                    sid = "1005227941",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 1,
                            name = "핑핑이1",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        ),
                    ),
                    url = "https://map.naver.com/p/entry/place/1492901893",
                    placeName = "퍼즈앤스틸",
                    px = 126.9713426,
                    py = 37.5303303,
                    type = "음식점"
                )
            )
        )

        BDDMockito.given(pingService.getAllNonMemberPings(uuid)).willReturn(response)

        //when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PingApi.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("uuid", uuid)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( //rest docs
                resultHandler.document(
                    RequestDocumentation.queryParameters(
                        RequestDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("eventName").description("이벤트 이름"),
                        fieldWithPath("neighborhood").description("추천 지역 이름"),
                        fieldWithPath("px").description("중심 경도"),
                        fieldWithPath("py").description("중심 위도"),
                        fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                        fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                        fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                        fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                        fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                        fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                        fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                        fieldWithPath("recommendPings[].type").description("업종"),
                        fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                        fieldWithPath("nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                        fieldWithPath("pings[].sid").description("장소 sid"),
                        fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                        fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].url").description("장소 url"),
                        fieldWithPath("pings[].placeName").description("장소 이름"),
                        fieldWithPath("pings[].px").description("경도"),
                        fieldWithPath("pings[].py").description("위도"),
                        fieldWithPath("pings[].type").description("업종"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "전체 핑 불러오기",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("전체 핑 불러오기")
                        .queryParameters(
                            ResourceDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID")
                        )
                        .responseFields(
                            fieldWithPath("eventName").description("이벤트 이름"),
                            fieldWithPath("neighborhood").description("추천 지역 이름"),
                            fieldWithPath("px").description("중심 경도"),
                            fieldWithPath("py").description("중심 위도"),
                            fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                            fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                            fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                            fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                            fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                            fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                            fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                            fieldWithPath("recommendPings[].type").description("업종"),
                            fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                            fieldWithPath("nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                            fieldWithPath("pings[].sid").description("장소 sid"),
                            fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                            fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].url").description("장소 url"),
                            fieldWithPath("pings[].placeName").description("장소 이름"),
                            fieldWithPath("pings[].px").description("경도"),
                            fieldWithPath("pings[].py").description("위도"),
                            fieldWithPath("pings[].type").description("업종"),
                        )
                )
            )
    }

    @Test
    @DisplayName("개별 핑 불러오기")
    fun getNonMemberPing() {
        //given
        val nonMemberId = 1L
        val response = GetNonMemberPing.Response(
            pings = listOf(
                GetAllNonMemberPings.Ping(
                    iconLevel = 0,
                    sid = "1005227941",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = nonMemberId,
                            name = "쏘야",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        )
                    ),
                    url = "https://map.naver.com/p/entry/place/1072787710",
                    placeName = "도토리",
                    px = 126.9727984,
                    py = 37.5319087,
                    type = "음식점"
                ),
                GetAllNonMemberPings.Ping(
                    iconLevel = 0,
                    sid = "1005227941",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = nonMemberId,
                            name = "쏘야",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        )
                    ),
                    url = "https://map.naver.com/p/entry/place/1092976589",
                    placeName = "당케커피",
                    px = 126.971301,
                    py = 37.5314638,
                    type = "음식점"
                )
            )
        )

        BDDMockito.given(pingService.getNonMemberPing(nonMemberId)).willReturn(response)

        //when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PingApi.PING_NONMEMBER_ID, nonMemberId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )

        //then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    PayloadDocumentation.responseFields(
                        fieldWithPath("pings[].iconLevel").description("아이콘 레벨: 0"),
                        fieldWithPath("pings[].sid").description("장소 sid"),
                        fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                        fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].url").description("장소 URL"),
                        fieldWithPath("pings[].placeName").description("장소 이름"),
                        fieldWithPath("pings[].px").description("경도"),
                        fieldWithPath("pings[].py").description("위도"),
                        fieldWithPath("pings[].type").description("업종"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "개별 핑 불러오기",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("개별 핑 불러오기")
                        .responseFields(
                            fieldWithPath("pings[].iconLevel").description("아이콘 레벨: 0"),
                            fieldWithPath("pings[].sid").description("장소 sid"),
                            fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                            fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].url").description("장소 URL"),
                            fieldWithPath("pings[].placeName").description("장소 이름"),
                            fieldWithPath("pings[].px").description("경도"),
                            fieldWithPath("pings[].py").description("위도"),
                            fieldWithPath("pings[].type").description("업종"),
                        )
                )
            )
    }

    @Test
    @DisplayName("추천 핑 조회")
    fun getRecommendPings() {
        // given
        val uuid = "test"
        val radiusInKm = 1.0
        val response = GetRecommendPings.Response(
            recommendPings = listOf(
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1445446311",
                    placeName = "꾸아 광교점",
                    url = "https://map.naver.com/p/entry/place/1445446311",
                    px = 127.0548454,
                    py = 37.2938313,
                    type = "음식점",
                ),
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1250904288",
                    placeName = "옴레스토랑 광교점",
                    url = "https://map.naver.com/p/entry/place/1250904288",
                    px = 127.0505683,
                    py = 37.2903113,
                    type = "음식점",
                )
            ),
        )
        BDDMockito.given(pingService.getRecommendPings(uuid, radiusInKm)).willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PingApi.PING_RECOMMEND)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("uuid", uuid)
                .queryParam("radiusInKm", radiusInKm.toString())
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    RequestDocumentation.queryParameters(
                        RequestDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID"),
                        RequestDocumentation.parameterWithName("radiusInKm").description("추천 반경 범위 (km)"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                        fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                        fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                        fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                        fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                        fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                        fieldWithPath("recommendPings[].type").description("업종"),
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "추천 핑 조회",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("추천 핑 조회")
                        .queryParameters(
                            ResourceDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID"),
                            ResourceDocumentation.parameterWithName("radiusInKm").description("추천 반경 범위 (km)")
                        )
                        .responseFields(
                            fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                            fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                            fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                            fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                            fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                            fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                            fieldWithPath("recommendPings[].type").description("업종"),
                        )
                )
            )
    }

    @Test
    @DisplayName("비회원 핑 업데이트")
    fun updateNonMemberPings() {
        // given
        val request = UpdateNonMemberPings.Request(
            nonMemberId = 1L,
            bookmarkUrls = listOf("https://naver.me/Fqimcb8B"),
            storeUrls = listOf("https://naver.me/FuVzL1bq")
        )

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.put(PingApi.BASE_URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("nonMemberId").description("비회원 ID"),
                        fieldWithPath("bookmarkUrls").description("업데이트할 북마크 URL 목록"),
                        fieldWithPath("storeUrls").description("업데이트할 스토어 URL 목록")
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 핑 업데이트",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원 핑 업데이트")
                        .requestFields(
                            fieldWithPath("nonMemberId").description("비회원 ID"),
                            fieldWithPath("bookmarkUrls").description("업데이트할 북마크 URL 목록"),
                            fieldWithPath("storeUrls").description("업데이트할 스토어 URL 목록")
                        )
                )
            )
    }

    @Test
    @DisplayName("비회원 모든 핑 리프레쉬")
    fun refreshAllNonMemberPings() {
        // given
        val uuid = "test"
        val response = GetAllNonMemberPings.Response(
            eventName = "수원 광교 힙쟁이들",
            neighborhood = "수원 광교",
            recommendPings = listOf(
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1445446311",
                    placeName = "꾸아 광교점",
                    url = "https://map.naver.com/p/entry/place/1445446311",
                    px = 127.0548454,
                    py = 37.2938313,
                    type = "음식점",
                ),
                GetRecommendPings.RecommendPing(
                    iconLevel = 10,
                    sid = "1250904288",
                    placeName = "옴레스토랑 광교점",
                    url = "https://map.naver.com/p/entry/place/1250904288",
                    px = 127.0505683,
                    py = 37.2903113,
                    type = "음식점",
                )
            ),
            nonMembers = listOf(
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 1,
                    name = "핑핑이1",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                ),
                GetAllNonMemberPings.NonMember(
                    nonMemberId = 2,
                    name = "핑핑이2",
                    profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                )
            ),
            px = 127.00001,
            py = 37.00001,
            pingLastUpdateTime = "19분",
            pings = listOf(
                GetAllNonMemberPings.Ping(
                    iconLevel = 2,
                    sid = "1445446311",
                    nonMembers = listOf(
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 1,
                            name = "핑핑이1",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        ),
                        GetAllNonMemberPings.NonMember(
                            nonMemberId = 2,
                            name = "핑핑이2",
                            profileSvg = "https://kr.object.ncloudstorage.com/moping-image/profile1.svg"
                        )
                    ),
                    url = "https://map.naver.com/p/entry/place/1946678040",
                    placeName = "호이",
                    px = 126.971178,
                    py = 37.5302481,
                    type = "음식점"
                )
            )
        )

        BDDMockito.given(pingService.refreshAllNonMemberPings(uuid)).willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PingApi.PING_REFRESH_ALL)
                .param("uuid", uuid)
                .contentType(MediaType.APPLICATION_JSON)
        )
        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    RequestDocumentation.queryParameters(
                        RequestDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("eventName").description("이벤트 이름"),
                        fieldWithPath("neighborhood").description("추천 지역 이름"),
                        fieldWithPath("px").description("중심 경도"),
                        fieldWithPath("py").description("중심 위도"),
                        fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                        fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                        fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                        fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                        fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                        fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                        fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                        fieldWithPath("recommendPings[].type").description("업종"),
                        fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                        fieldWithPath("nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                        fieldWithPath("pings[].sid").description("장소 sid"),
                        fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                        fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                        fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                        fieldWithPath("pings[].url").description("장소 URL"),
                        fieldWithPath("pings[].placeName").description("장소 이름"),
                        fieldWithPath("pings[].px").description("경도"),
                        fieldWithPath("pings[].py").description("위도"),
                        fieldWithPath("pings[].type").description("업종"),
                    )
                )
            )
            .andDo( //swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "비회원 모든 핑 리프레쉬",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("비회원 모든 핑 리프레쉬")
                        .queryParameters(
                            ResourceDocumentation.parameterWithName("uuid").description("이벤트 식별자 UUID")
                        )
                        .responseFields(
                            fieldWithPath("eventName").description("이벤트 이름"),
                            fieldWithPath("neighborhood").description("추천 지역 이름"),
                            fieldWithPath("px").description("중심 경도"),
                            fieldWithPath("py").description("중심 위도"),
                            fieldWithPath("pingLastUpdateTime").description("마지막 핑 업데이트 시간"),
                            fieldWithPath("recommendPings[].iconLevel").description("추천 장소 아이콘 레벨"),
                            fieldWithPath("recommendPings[].sid").description("추천 장소 ID"),
                            fieldWithPath("recommendPings[].placeName").description("추천 장소 이름"),
                            fieldWithPath("recommendPings[].url").description("추천 장소 URL"),
                            fieldWithPath("recommendPings[].px").description("추천 장소 경도"),
                            fieldWithPath("recommendPings[].py").description("추천 장소 위도"),
                            fieldWithPath("recommendPings[].type").description("업종"),
                            fieldWithPath("nonMembers[].nonMemberId").description("비회원의 id"),
                            fieldWithPath("nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].iconLevel").description("아이콘 레벨\n4:가장 많이 겹침\n3:그다음\n2:그다음\n1:나머지"),
                            fieldWithPath("pings[].sid").description("장소 sid"),
                            fieldWithPath("pings[].nonMembers[].nonMemberId").description("비회원 id"),
                            fieldWithPath("pings[].nonMembers[].name").description("비회원 이름"),
                            fieldWithPath("pings[].nonMembers[].profileSvg").description("프로필 svg url"),
                            fieldWithPath("pings[].url").description("장소 URL"),
                            fieldWithPath("pings[].placeName").description("장소 이름"),
                            fieldWithPath("pings[].px").description("경도"),
                            fieldWithPath("pings[].py").description("위도"),
                            fieldWithPath("pings[].type").description("업종"),
                        )
                )
            )
    }

    @Test
    @DisplayName("회원 핑 삭제")
    fun deletePing() {
        // given
        val request = MemberPing.Request(
            sid = "testSid"
        )
        val jsonRequest = """
        {
            "sid": "${request.sid}"
        }
    """.trimIndent()

        doNothing().`when`(pingService).deletePing(any(), any())

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete(PingApi.PING_MEMBER)
                .header("Authorization", "Bearer validAccessToken123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // REST Docs
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    requestFields(
                        fieldWithPath("sid").description("삭제할 장소 ID")
                    )
                )
            )
            .andDo( // Swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "멤버 핑 삭제",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("멤버의 핑을 삭제하는 API")
                        .requestHeaders(
                            headerWithName("Authorization").description("Access Token")
                        )
                        .requestFields(
                            fieldWithPath("sid").description("삭제할 장소 ID")
                        )
                )
            )
    }
}