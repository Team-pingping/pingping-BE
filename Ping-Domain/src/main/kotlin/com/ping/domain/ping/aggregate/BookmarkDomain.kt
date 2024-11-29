package com.ping.domain.ping.aggregate

import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import com.ping.domain.ping.repository.BookmarkRepository

data class BookmarkDomain (
    val name: String,
    val px: Double,
    val py: Double,
    val sid: String,
    val address: String,
    val mcidName: String,
    val url: String,
) {
    companion object {
        fun validateSidExists(sid: String, bookmarkRepository: BookmarkRepository) {
            if (!bookmarkRepository.existsBySid(sid)) {
                throw CustomException(ExceptionContent.BOOKMARK_NOT_FOUND)
            }
        }
    }
}