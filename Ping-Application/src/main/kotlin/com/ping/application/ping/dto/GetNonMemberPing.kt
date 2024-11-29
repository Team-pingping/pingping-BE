package com.ping.application.ping.dto

import com.ping.application.ping.dto.GetAllNonMemberPings

interface GetNonMemberPing {
    data class Response(
        val pings: List<GetAllNonMemberPings.Ping>,
    )
}