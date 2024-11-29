package com.ping.client.naver.place

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NaverApiClient(
    @Value("\${naver.client.id}") private val clientId: String,
    @Value("\${naver.client.secret}") private val clientSecret: String,
    @Value("\${naver.cloud.id}") private val cloudId: String,
    @Value("\${naver.cloud.secret}") private val cloudSecret: String
) {

    fun searchPlaces(keyword: String): List<NaverApiResponse.NaverPlace> {
        val client = WebClient.create("https://openapi.naver.com/v1/search/local.json")
        val response = client.get()
            .uri { uriBuilder ->
                uriBuilder.queryParam("query", keyword).queryParam("display", 10).build()
            }
            .header("X-Naver-Client-Id", clientId)
            .header("X-Naver-Client-Secret", clientSecret)
            .retrieve()
            .bodyToMono(NaverApiResponse.NaverResponse::class.java)
            .block()

        return response?.items ?: emptyList()
    }

    fun getGeocodeAddress(address: String): Pair<Double?, Double?> {
        val client = WebClient.create("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
        val response = client.get()
            .uri { uriBuilder -> uriBuilder.queryParam("query", address).build() }
            .header("X-NCP-APIGW-API-KEY-ID", cloudId)
            .header("X-NCP-APIGW-API-KEY", cloudSecret)
            .retrieve()
            .bodyToMono(NaverApiResponse.NaverGeocodeResponse::class.java)
            .block()

        val location = response?.addresses?.firstOrNull()
        return if (location != null) {
            Pair(location.x, location.y)
        } else {
            Pair(null, null)
        }
    }

    fun getReverseGeocode(px: Double, py: Double): String? {
        val client = WebClient.create("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc")
        val response = client.get()
            .uri { uriBuilder ->
                uriBuilder.queryParam("coords", "$px,$py")
                    .queryParam("output", "json")
                    .queryParam("orders", "roadaddr")
                    .build()
            }
            .header("X-NCP-APIGW-API-KEY-ID", cloudId)
            .header("X-NCP-APIGW-API-KEY", cloudSecret)
            .retrieve()
            .bodyToMono(NaverApiResponse.ReverseGeocodeResponse::class.java)
            .block()

        // 도로명 주소의 모든 구성 요소를 조합하여 반환
        val land = response?.results?.firstOrNull()?.land
        return if (land != null) {
            "${land.name} ${land.number1}${if (land.number2?.isNotEmpty() == true) "-${land.number2}" else ""}"
        } else {
            null
        }
    }
}
