package com.ping.api.event

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ping.api.global.BaseRestDocsTest
import com.ping.application.event.service.EventService
import com.ping.application.event.dto.CreateEvent
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(EventController::class)
class EventControllerTest : BaseRestDocsTest() {

    @MockBean
    private lateinit var eventService: EventService

    private val tag = "이벤트"

    @Test
    @DisplayName("이벤트 생성")
    fun createEvent() {
        // given
        val request = CreateEvent.Request(
            neighborhood = "홍익대학교",
            px = 126.9256698,
            py = 37.5507353,
            eventName = "힙한모임"
        )
        val createEventResponse = CreateEvent.Response(
            shareUrl = "https://moping.co.kr/share/힙한모임/홍익대학교/12345678"
        )

        given(eventService.create(request)).willReturn(createEventResponse)

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(EventApi.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        )

        // then
        result.andExpect(status().isOk)
            .andDo( //rest docs
                resultHandler.document(
                    requestFields(
                        fieldWithPath("neighborhood").description("이벤트 장소"),
                        fieldWithPath("px").description("이벤트 장소의 경도"),
                        fieldWithPath("py").description("이벤트 장소의 위도"),
                        fieldWithPath("eventName").description("이벤트 이름")
                    ),
                    responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data.shareUrl").description("공유할 이벤트 URL")
                    )
                )
            )
            .andDo( //swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "이벤트 생성",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("이벤트 생성")
                        .requestFields(
                            fieldWithPath("neighborhood").description("이벤트 장소"),
                            fieldWithPath("px").description("이벤트 장소의 경도"),
                            fieldWithPath("py").description("이벤트 장소의 위도"),
                            fieldWithPath("eventName").description("이벤트 이름")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.shareUrl").description("공유할 이벤트 URL")
                        )
                )
            )
    }
}
