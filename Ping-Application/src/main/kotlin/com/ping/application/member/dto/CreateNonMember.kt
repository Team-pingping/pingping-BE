package com.ping.application.member.dto

interface CreateNonMember {
    data class Request(
        val uuid: String,
        val name: String,
        val password: String,
        val bookmarkUrls: List<String>,
        val storeUrls: List<String>,
    )
}