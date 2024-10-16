package com.ping.domain.nonmember.aggregate

data class NonMember(
    val id: Long,
    val name: String,
    val password: String,
    val shareUrl: ShareUrl
) {
    companion object {
        fun of(
            name: String,
            password: String,
            shareUrl: ShareUrl
        ): NonMember {
            return NonMember(0L, name, password, shareUrl)
        }
    }
}