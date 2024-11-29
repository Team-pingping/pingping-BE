package com.ping.client.naver.place

class NaverApiResponse {
    data class NaverResponse(
        val items: List<NaverPlace>
    )

    data class NaverPlace(
        val title: String,
        val address: String,
        val mapx: Double,
        val mapy: Double
    )

    data class Address(
        val roadAddress: String,
        val jibunAddress: String,
        val x: Double,
        val y: Double
    )

    data class NaverGeocodeResponse(
        val addresses: List<Address>
    )

    data class ReverseGeocodeResponse(
        val results: List<Result>
    )

    data class Result(
        val name: String,
        val land: Land
    )

    data class Land(
        val name: String,        // 도로명
        val number1: String,      // 본번
        val number2: String? = "" // 부번 (없을 수도 있음)
    )
}