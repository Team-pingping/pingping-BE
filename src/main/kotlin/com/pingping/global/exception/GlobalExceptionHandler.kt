package com.pingping.global.exception

import com.pingping.global.common.CommonResponse
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

    // 공통 에러 응답 생성 메서드
    private fun generateErrorResponse(status: HttpStatus, message: String?): ResponseEntity<CommonResponse<Nothing?>> {
        val errorMessage = message ?: "Unexpected error occurred"
        val errorResponse = CommonResponse.of<Nothing?>(status, errorMessage)
        return ResponseEntity(errorResponse, status)
    }

    // 예외 발생시 로그 기록 및 응답 처리
    private fun logAndGenerateErrorResponse(e: Exception, status: HttpStatus, message: String? = null): ResponseEntity<CommonResponse<Nothing?>> {
        log.error(e) { "${e.javaClass.simpleName} occurred: ${e.message}" }
        return generateErrorResponse(status, message ?: e.message)
    }

    // 커스텀 Exception 처리
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(exception, exception.content.httpStatus, exception.message)
    }

    // 모든 Exception 처리
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(e: Exception): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
    }

    // NoSuchElementException 처리
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.NOT_FOUND, "Resource not found")
    }

    // EmptyResultDataAccessException 처리
    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.NOT_FOUND, "Resource not found")
    }

    // HttpMessageNotReadableException 처리
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonException(e: HttpMessageNotReadableException): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid JSON format")
    }

    // HttpRequestMethodNotSupportedException 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleRequestMethodException(e: HttpRequestMethodNotSupportedException): ResponseEntity<CommonResponse<Nothing?>> {
        return logAndGenerateErrorResponse(e, HttpStatus.METHOD_NOT_ALLOWED, "API does not support this request method. Please check the endpoint.")
    }
}
