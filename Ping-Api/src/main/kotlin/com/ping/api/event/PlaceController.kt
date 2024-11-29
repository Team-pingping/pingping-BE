package com.ping.api.event

import com.ping.application.event.service.PlaceService
import com.ping.application.event.dto.GeocodePlace
import com.ping.application.event.dto.SearchPlace
import com.ping.common.exception.SuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(
    private val placeService: PlaceService,
) {

    @GetMapping(PlaceApi.SEARCH)
    fun searchPlace(@RequestParam("keyword") keyword: String): ResponseEntity<SuccessResponse<List<SearchPlace.Response>>> {
        val response = placeService.searchPlace(keyword);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "장소 검색 성공", response))
    }

    @GetMapping(PlaceApi.GEOCODE)
    fun getGeocodePlace(@RequestParam("address") address: String): ResponseEntity<SuccessResponse<GeocodePlace.Response>> {
        val response = placeService.getGeocodeAddress(address)
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "좌표 검색 성공", response))
    }

    @GetMapping(PlaceApi.REVERSE_GEOCODE)
    fun getReverseGeocode(@RequestParam("px") px: Double, @RequestParam("py") py: Double): ResponseEntity<SuccessResponse<String?>> {
        val response = placeService.getReverseGeocode(px, py)
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "도로명 주소 조회 성공", response))
    }
}