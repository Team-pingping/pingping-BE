package com.ping.client.naver.map

class NaverBookmarkResponse {
    data class BookmarkLists(
        val bookmarkList: List<Bookmark>
    )

    data class Bookmark(
        val name: String,
        val px: Double,
        val py: Double,
        val sid: String,
        val address: String,
        val mcidName: String,
    )
}