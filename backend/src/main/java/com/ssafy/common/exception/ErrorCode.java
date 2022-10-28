package com.ssafy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/* 사용자 정의 에러코드 정의 : enum */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    /* 403 FORBIDDEN : 권한이 없는 사용자 */
    NOT_ALLOWED_USER(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자를 찾을 수 없습니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

    /* 커스텀 예외들 */
    SESSION_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 세션 타입입니다." );

    private final HttpStatus httpStatus;
    private final String message;
//    private final String code;

    /*
    이때, HttpStatus를 따르지 않고 USER_NOT_FOUND(901, "유저 없음") 와 같이 custom해서 만들어도 된다.
    커스텀하는 경우에는 HttpStatus타입이 아닌 Integer타입으로 바꿔야 한다
    */

}
