package com.ping.application.event.service

import com.ping.application.event.dto.GeocodePlace
import com.ping.application.event.dto.SearchPlace
import com.ping.client.naver.place.NaverApiClient
import com.ping.client.naver.place.NaverApiResponse
import com.ping.common.util.AlternativeQueryProvider
import com.ping.domain.event.repository.SubwayRepository
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val naverApiClient: NaverApiClient,
    private val subwayRepository: SubwayRepository
) {

    fun searchPlace(keyword: String): List<SearchPlace.Response> {
        val queryList = AlternativeQueryProvider.getQueries(keyword)

        val resultsPerQuery = queryList.map { query ->
            var modifiedQuery = query
            subwayRepository.findByStation(query)?.let { modifiedQuery = "${query}역" }
            naverApiClient.searchPlaces(modifiedQuery)
        }

        val combinedResults = mutableListOf<NaverApiResponse.NaverPlace>()

        val maxSize = resultsPerQuery.maxOfOrNull { it.size } ?: 0
        for (i in 0 until maxSize) {
            for (resultList in resultsPerQuery) {
                if (i < resultList.size) {
                    combinedResults.add(resultList[i])
                }
            }
        }

        val uniqueResults = combinedResults.distinctBy { it.title }
        val limitedResults = uniqueResults.take(5)

        return limitedResults.map {
            SearchPlace.Response(
                name = it.title.replace("<b>", "").replace("</b>", ""),
                address = it.address,
                px = it.mapx,
                py = it.mapy
            )
        }
    }

    fun getGeocodeAddress(address: String): GeocodePlace.Response {
        val (latitude, longitude) = naverApiClient.getGeocodeAddress(address)
        return GeocodePlace.Response(
            address = address,
            px = latitude,
            py = longitude
        )
    }

    fun getReverseGeocode(px: Double, py: Double): String? {
        val roundedPx = String.format("%.4f", px).toDouble()
        val roundedPy = String.format("%.4f", py).toDouble()
        return naverApiClient.getReverseGeocode(roundedPx, roundedPy)
    }
}