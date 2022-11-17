package com.ssafy.common.exception;

import com.google.api.Http;
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
    SESSION_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 세션 타입입니다."),
    SESSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 세션 id 입니다"),
    SESSION_MESSAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 세션 메세지 id 입니다"),
    ALREADY_FOLLOW(HttpStatus.CONFLICT, "이미 팔로우한 사용자입니다."),
    INVALID_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    INVALID_SESSION_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 세션 타입입니다."),
    //  Challenge 관련 예외
    CHALLENGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 챌린지 id 입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "존재하지 않는 팔로워-팔로잉 관계입니다."),

    CHALLENEGE_REWARD_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"이미 첼린지 리워드 설정을 하였습니다."),

    LEADER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id를 가진 챌린지 리더가 없습니다." ),

    CHALLENGE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB INSERT 중 문제가 발생했습니다." ),
    CHALLENGE_INVITE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 초대입니다."),

    CHALLENGEINFO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 정보입니다."),

    CHALLENGEAUTHID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 인증 id입니다."),
    CHALLENGE_AUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 인증입니다."),

    CHALLENGE_ALREADY_VOTED(HttpStatus.BAD_REQUEST,"이미 투표한 사용자입니다." ),
    BOTH_USER_NOT_EXIST_CHALLENGE(HttpStatus.BAD_REQUEST, "두 사용자 모두 해당 챌린지에 없습니다." ),
    AUTH_USER_NOT_EXIST_CHALLENGE(HttpStatus.BAD_REQUEST, "투표 요청할 유저가 해당 챌린지에 없습니다." ),
    VOTE_USER_NOT_EXIST_CHALLENGE(HttpStatus.BAD_REQUEST, "투표를 요청한 유저가 해당 챌린지에 없습니다." ),

    INVALID_AUTH_USER(HttpStatus.BAD_REQUEST, "해당 투표를 요청한 유저 본인이 아닙니다." ),
    CHALLENGEINVITE_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 초대장 사용자입니다." ),
    SESSION_SAVE_ERROR(HttpStatus.BAD_REQUEST, "세션 생성중 에러 발생. 세션이 생성되지 않았습니다." ),
    CHALLENGE_DATE_ERROR(HttpStatus.BAD_REQUEST, "챌린지 시작일이 종료일보다 늦습니다." ),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 사용자 아이디 입니다." ),
    INVALID_GIFTICON_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 기프티콘 아이디 입니다." ), 
    SESSION_MESSAGE_CREATE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "세션 메세지 생성 중 서버 오류가 발생했습니다. 관리자에게 문의하세요" ),
    CHALLENGE_INFO_NOT_FOUND( HttpStatus.NOT_FOUND, "해당 챌린지 ID에 해당하는 챌린지 INFO를 찾을 수 없습니다." ),
    CHALLENGE_INVITE_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 초대한 사용자입니다." ),
    CHALLENGE_CANT_INVITE_LEADER(HttpStatus.BAD_REQUEST, "챌린지 리더는 초대할 수 없습니다." ),
    CHALLENGEINVITE_CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지 초대장입니다." ),
    GIFTICON_NOT_FOUND( HttpStatus.NOT_FOUND, "해당 기프티콘 ID에 해당하는 기프티콘을 찾을 수 없습니다." ),
    GIFTICON_USER_NOT_FOUND( HttpStatus.NOT_FOUND, "사용자에게 기프티콘 ID에 해당하는 기프티콘이 없습니다." ), NOT_FOUND_NAME(HttpStatus.NOT_FOUND,"세션이름 없음" );

    private final HttpStatus httpStatus;
    private final String message;
//    private final String code;

    /*
    이때, HttpStatus를 따르지 않고 USER_NOT_FOUND(901, "유저 없음") 와 같이 custom해서 만들어도 된다.
    커스텀하는 경우에는 HttpStatus타입이 아닌 Integer타입으로 바꿔야 한다
    */

    }
