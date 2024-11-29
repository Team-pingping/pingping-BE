package com.ping.common.util

import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent

object ValidationUtil {
    //이름 공백, 특수문자, 숫자 불가
    fun validateName(name: String) {
        val namePattern = "^[가-힣a-zA-Z]{1,6}\$".toRegex()
        if (!namePattern.matches(name)) {
            throw CustomException(ExceptionContent.INVALID_NAME_FORMAT)
        }
    }

    // 비밀번호 형식 검사 (4자리 숫자)
    fun validatePassword(password: String) {
        if (!password.matches(Regex("\\d{4}"))) {
            throw CustomException(ExceptionContent.INVALID_PASSWORD_FORMAT)
        }
    }
}