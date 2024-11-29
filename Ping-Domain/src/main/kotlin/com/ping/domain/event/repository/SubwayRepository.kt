package com.ping.domain.event.repository

import com.ping.domain.event.aggregate.SubwayDomain

interface SubwayRepository {
    fun findByStation(station: String) : SubwayDomain?
}