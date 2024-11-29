package com.ping.common.exception

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.NoSuchElementException
import io.github.oshai.kotlinlogging.KotlinLogging

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {}
    val errorPrefix = "UNKNOWN"
    val errorNum = 1

    // 공통 에러 응답 생성 메서드
    private fun generateErrorResponse(status: HttpStatus, message: String?): ResponseEntity<ErrorResponse<Nothing?>> {
        val errorMessage = message ?: "알 수 없는 에러가 발생했습니다."
        val errorResponse = ErrorResponse.of<Nothing?>(status, errorPrefix, errorNum, errorMessage)
        return ResponseEntity(errorResponse, status)
    }

    // 예외 발생시 로그 기록 및 응답 처리
    private fun logAndGenerateErrorResponse(
        e: Exception,
        status: HttpStatus,
        message: String? = null
    ): ResponseEntity<ErrorResponse<Nothing?>> {
        log.error { "${e.javaClass.simpleName} occurred: ${e.message}" }
        return generateErrorResponse(status, message ?: e.message)
    }

    private fun logAndCustomErrorResponse(
        e: Exception,
        status: HttpStatus,
        errorPrefix: String,
        errorNum: Int,
        message: String
    ): ResponseEntity<ErrorResponse<Nothing?>> {
        log.error { "${e.javaClass.simpleName} occurred: ${e.message}" }
        val errorResponse = ErrorResponse.of<Nothing?>(status, errorPrefix, errorNum, message)
        return ResponseEntity(errorResponse, status)
    }

    // 커스텀 Exception 처리
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ErrorResponse<Nothing?>> {
        return logAndCustomErrorResponse(exception, exception.content.httpStatus, exception.content.errorPrefix,exception.content.errorNum ,exception.content.message)
    }

    // 모든 Exception 처리
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(e: Exception): ResponseEntity<ErrorResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
    }

    // NoSuchElementException 처리
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.NOT_FOUND, "Resource not found")
    }

    // HttpRequestMethodNotSupportedException 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleRequestMethodException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse<Nothing?>> {
        return logAndGenerateErrorResponse(
            e,
            HttpStatus.METHOD_NOT_ALLOWED,
            "API does not support this request method. Please check the endpoint."
        )
    }
}
