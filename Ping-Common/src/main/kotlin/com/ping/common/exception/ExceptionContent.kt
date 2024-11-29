package com.ping.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionContent(
    val httpStatus: HttpStatus,
    val errorPrefix: String,
    val errorNum: Int,
    val message: String
) {
    // NonMember 관련 예외
    NON_MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "NONMEMBER",1,"비회원 생성 실패: 이미 존재하는 비회원입니다."),
    NON_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "NONMEMBER",2,"비회원 로그인 실패: 해당 비회원 정보를 찾을 수 없습니다."),
    NON_MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "NONMEMBER",3,"비회원 로그인 실패: 비밀번호가 일치하지 않습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "NONMEMBER",4,"이름은 공백, 특수문자, 숫자를 포함할 수 없으며, 6글자 이하여야 합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "NONMEMBER",5,"비밀번호는 4자리 숫자여야 합니다."),

    // ShareUrl 관련 예외
    INVALID_SHARE_URL(HttpStatus.NOT_FOUND, "SHARE URL",1,"유효하지 않은 공유 URL입니다."),

    //북마크 관련 예외
    INVALID_BOOKMARK_URL(HttpStatus.BAD_REQUEST, "BOOKMARK",1,"북마크 링크가 아니에요"),
    INVALID_STORE_URL(HttpStatus.BAD_REQUEST, "BOOKMARK",2,"가게 링크가 아니에요"),
    INVALID_URL(HttpStatus.BAD_REQUEST,"BOOKMARK",3,"유효한 링크가 아니에요"),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND,"BOOKMARK",4,"해당 장소의 정보가 존재하지 않습니다."),

    // token
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN",1,"유효하지 않은 토큰입니다. 다시 로그인해 주세요."),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "TOKEN",2,"토큰이 요청 헤더에 없습니다."),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "TOKEN",3,"해당 토큰은 사용이 금지되었습니다. 다시 로그인해 주세요."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN",4,"토큰이 만료되었습니다. 새로운 토큰을 발급받으세요."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN",5,"세션이 만료되었습니다. 다시 로그인해 주세요."),

    // NonMemberPlace 관련 예외
    NON_MEMBER_PLACE_ALREADY_EXISTS(HttpStatus.CONFLICT, "NONMEMBERPLACE", 1, "이미 저장된 장소입니다. 다른 장소를 추가해주세요"),
    NON_MEMBER_PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "NONMEMBERPLACE", 2, "사용자가 저장하지 않은 장소입니다."),
}