package com.ping.domain.ping.service

import com.ping.client.naver.map.NaverMapClient
import com.ping.common.util.UrlUtil
import com.ping.domain.ping.aggregate.BookmarkDomain
import org.springframework.stereotype.Service

@Service
class PingUrlService(
    private val naverMapClient: NaverMapClient
) {
    fun bookmarkUrlToBookmarks(url: String): List<BookmarkDomain> {
        val bookmarks = mutableListOf<BookmarkDomain>()

        val expandedUrl = UrlUtil.expandShortUrl(url)
        val bookmarkList = naverMapClient.bookmarkUrlToBookmarkLists(expandedUrl).bookmarkList

        bookmarkList.forEach { bookmark ->
            bookmarks.add(
                BookmarkDomain(
                    name = bookmark.name,
                    px = bookmark.px,
                    py = bookmark.py,
                    sid = bookmark.sid,
                    address = bookmark.address,
                    mcidName = bookmark.mcidName,
                    url = "https://map.naver.com/p/entry/place/${bookmark.sid}"
                )
            )
        }

        return bookmarks
    }

    fun storeUrlToBookmark(url: String): BookmarkDomain {
        val expandedUrl = UrlUtil.expandShortUrl(url)
        val store = naverMapClient.storeUrlToBookmark(expandedUrl)

        return BookmarkDomain(
            name = store.name,
            px = store.px,
            py = store.py,
            sid = store.sid,
            address = store.address,
            mcidName = store.mcidName,
            url = url
        )
    }
}