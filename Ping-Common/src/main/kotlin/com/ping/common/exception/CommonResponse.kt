package com.ping.common.exception

import org.springframework.http.HttpStatus

data class CommonResponse<D>(
        val code: Int,
        val errorCode: String,
        val message: String,
        val data: D? = null
) {
    companion object {
        fun <D> of(status: HttpStatus, codePrefix: String, codeNum: Int, message: String, data: D? = null): CommonResponse<D> {
            val errorCode="$codePrefix-$codeNum"
            return CommonResponse(status.value(), errorCode, message, data)
        }
    }
}