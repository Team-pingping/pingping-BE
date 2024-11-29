package com.ping.common.exception

import org.springframework.http.HttpStatus

data class SuccessResponse<D>(
    val code: Int,
    val message: String,
    val data: D? = null
) {
    companion object {
        fun <D> of(status: HttpStatus, message: String, data: D? = null): SuccessResponse<D> {
            return SuccessResponse(status.value(), message, data)
        }
    }
}