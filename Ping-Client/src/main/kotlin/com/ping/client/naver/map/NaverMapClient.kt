package com.ping.client.naver.map

import com.fasterxml.jackson.databind.ObjectMapper
import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NaverMapClient(
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val BOOKMARK_URL_PATTERN = "/folder/"
        const val STORE_URL_PATTERN = "/place/"
    }

    fun bookmarkUrlToBookmarkLists(url: String): NaverBookmarkResponse.BookmarkLists {
        if (url.contains(STORE_URL_PATTERN)) throw CustomException(ExceptionContent.INVALID_BOOKMARK_URL)

        try {
            val shareId = url.split("/").lastOrNull()?.split("?")?.firstOrNull()?.replace("%", "")
            val webclient = WebClient.create("https://pages.map.naver.com")
            return webclient.get()
                .uri("/save-pages/api/maps-bookmark/v3/shares/${shareId}/bookmarks")
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    objectMapper.readValue(it, NaverBookmarkResponse.BookmarkLists::class.java)
                }
                .block()!!
        } catch (e: Exception) {
            throw CustomException(ExceptionContent.INVALID_URL)
        }
    }

    fun storeUrlToBookmark(url: String): NaverBookmarkResponse.Bookmark {
        if (url.contains(BOOKMARK_URL_PATTERN)) throw CustomException(ExceptionContent.INVALID_STORE_URL)

        try {
            val shareId = url.split("place/").lastOrNull()?.split("/home")?.firstOrNull()?.split("?")?.firstOrNull()
                ?.replace("%", "")!!
            val crawlingUrl = "https://pcmap.place.naver.com/restaurant/${shareId}/information"

            val randomUserAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Gecko/20100101 Firefox/85.0"//Random.nextInt(userAgents.size)]

            val document = Jsoup.connect(crawlingUrl)
                .userAgent(randomUserAgent)
                .referrer("https://www.naver.com")
                .get()

            val place = Regex("\"x\":\"([0-9.]+)\",\"y\":\"([0-9.]+)\"").find(document.html())!!
            val address = Regex("\"roadAddress\":\"([^\"]+)\"").find(document.html())?.groups?.get(1)?.value!!
            val mcidName = Regex("\"category\":\"([^\"]+)\"").find(document.html())?.groups?.get(1)?.value!!
            val placeName = document.selectFirst(".GHAhO")?.text()!!
            val px = place.groupValues[1]
            val py = place.groupValues[2]
            return NaverBookmarkResponse.Bookmark(
                name = placeName,
                px = px.toDouble(),
                py = py.toDouble(),
                sid = shareId,
                address = address,
                mcidName = mcidName,
            )
        } catch (e: Exception) {
            throw CustomException(ExceptionContent.INVALID_URL)
        }
    }
}