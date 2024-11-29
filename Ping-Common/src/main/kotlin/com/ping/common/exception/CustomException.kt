package com.ping.common.exception

class CustomException(
        val content: ExceptionContent
) : RuntimeException(content.message)