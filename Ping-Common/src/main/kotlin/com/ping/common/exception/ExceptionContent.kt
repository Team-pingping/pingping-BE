package com.ping.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionContent(
    val httpStatus: HttpStatus,
    val errorPrefix: String,
    val errorNum: Int,
    val message: String
) {
    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER", 1, "해당 사용자를 찾을 수 없습니다."),

    // NonMember 관련 예외
    NON_MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "NONMEMBER",1,"비회원 생성 실패: 이미 존재하는 비회원입니다."),
    NON_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "NONMEMBER",2,"비회원 로그인 실패: 해당 비회원 정보를 찾을 수 없습니다."),
    NON_MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "NONMEMBER",3,"비회원 로그인 실패: 비밀번호가 일치하지 않습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "NONMEMBER",4,"이름은 공백, 특수문자, 숫자를 포함할 수 없으며, 6글자 이하여야 합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "NONMEMBER",5,"비밀번호는 4자리 숫자여야 합니다."),

    // ShareUrl 관련 예외
    INVALID_SHARE_URL(HttpStatus.NOT_FOUND, "SHARE URL",1,"유효하지 않은 공유 URL입니다."),

    //북마크 관련 예외
    INVALID_BOOKMARK_URL(HttpStatus.BAD_REQUEST, "BOOKMARK",1,"북마크를 불러올 수 없습니다"),
    INVALID_STORE_URL(HttpStatus.BAD_REQUEST, "BOOKMARK",2,"가게 정보를 불러올 수 없습니다")
}