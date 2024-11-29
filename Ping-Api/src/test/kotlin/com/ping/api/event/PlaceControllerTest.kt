package com.ping.api.event

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ping.api.event.PlaceApi
import com.ping.api.event.PlaceController
import com.ping.api.global.BaseRestDocsTest
import com.ping.application.event.service.PlaceService
import com.ping.application.event.dto.GeocodePlace
import com.ping.application.event.dto.SearchPlace
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PlaceController::class)
class PlaceControllerTest : BaseRestDocsTest() {

    @MockBean
    private lateinit var placeService: PlaceService

    private val tag = "장소"

    @Test
    @DisplayName("장소 검색")
    fun searchPlace() {
        // given
        val keyword = "홍대"
        val response = listOf(
            SearchPlace.Response("홍대입구", "서울 마포구", 126.9784, 37.5665),
            SearchPlace.Response("홍익대학교", "서울 마포구", 126.9372, 37.5502)
        )
        given(placeService.searchPlace(keyword)).willReturn(response)

        // when
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PlaceApi.SEARCH)
                .param("keyword", keyword)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    queryParameters(
                        RequestDocumentation.parameterWithName("keyword").description("검색할 장소의 키워드")
                    ),
                    responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data[].name").description("장소 이름"),
                        fieldWithPath("data[].address").description("장소 주소"),
                        fieldWithPath("data[].px").description("장소 경도"),
                        fieldWithPath("data[].py").description("장소 위도")
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "키워드로 장소 검색",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("장소")
                        .description("키워드로 장소 검색")
                        .queryParameters(
                            parameterWithName("keyword").description("검색할 장소의 키워드")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data[].name").description("장소 이름"),
                            fieldWithPath("data[].address").description("장소 주소"),
                            fieldWithPath("data[].px").description("장소 경도"),
                            fieldWithPath("data[].py").description("장소 위도")
                        )
                )
            )
    }

    @Test
    @DisplayName("주소로 좌표 검색")
    fun getGeocodePlace() {
        // given
        val address = "서울시 중구 명동"
        val geocodeResponse = GeocodePlace.Response("서울시 중구 명동", 126.9784, 37.5665)
        given(placeService.getGeocodeAddress(address)).willReturn(geocodeResponse)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PlaceApi.GEOCODE)
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo( // rest docs
                resultHandler.document(
                    queryParameters(
                        RequestDocumentation.parameterWithName("address").description("좌표를 얻고자 하는 주소")
                    ),
                    responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data.address").description("검색된 주소"),
                        fieldWithPath("data.px").description("주소의 경도"),
                        fieldWithPath("data.py").description("주소의 위도")
                    )
                )
            )
            .andDo( // swagger
                MockMvcRestDocumentationWrapper.document(
                    identifier = "주소로 좌표 검색",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("주소로 좌표 검색")
                        .queryParameters(
                            parameterWithName("address").description("좌표를 얻고자 하는 주소")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.address").description("검색된 주소"),
                            fieldWithPath("data.px").description("주소의 경도"),
                            fieldWithPath("data.py").description("주소의 위도")
                        )
                )
            )
    }

    @Test
    @DisplayName("도로명 주소 조회")
    fun getReverseGeocode() {
        // given
        val px = 127.12345
        val py = 37.12345
        val response = "성남대로 123-45"

        given(placeService.getReverseGeocode(px, py)).willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get(PlaceApi.REVERSE_GEOCODE)
                .param("px", px.toString())
                .param("py", py.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    queryParameters(
                        RequestDocumentation.parameterWithName("px").description("경도"),
                        RequestDocumentation.parameterWithName("py").description("위도")
                    ),
                    responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("도로명 주소")
                    )
                )
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "경도와 위도를 기반으로 도로명 주소 조회",
                    resourceDetails =
                    ResourceSnippetParametersBuilder()
                        .tag(tag)
                        .description("경도와 위도를 기반으로 도로명 주소 조회")
                        .queryParameters(
                            RequestDocumentation.parameterWithName("px").description("경도"),
                            RequestDocumentation.parameterWithName("py").description("위도")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data").description("도로명 주소")
                        )
                )
            )
    }
}
