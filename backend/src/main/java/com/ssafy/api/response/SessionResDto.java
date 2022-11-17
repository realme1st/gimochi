package com.ssafy.api.response;

import com.ssafy.api.request.SessionReqDto;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionResDto {
    private Long userId;

    private String name;
    private Long sessionId;
    private Long sessionTypeId;
    private LocalDate expireTime;
    private LocalDate anniversary;

    @Builder
    public SessionResDto(Long userId, String name, Long sessionTypeId, LocalDate anniversary, Long sessionId) {
        this.userId = userId;
        this.name = name;
        this.expireTime = anniversary.plusDays(7);
        this.sessionTypeId = sessionTypeId;
        this.anniversary = anniversary;
        this.sessionId = sessionId;
    }

    public static Session createSessionEntity(SessionReqDto sessionReqDto, User user){
        System.out.println("사용자정의 : "+sessionReqDto.getName());
        if(sessionReqDto.getSessionTypeId() == 5){
            return Session.builder()
                    .anniversary(sessionReqDto.getAnniversary())
                    .expireTime(sessionReqDto.getAnniversary().plusDays(7))
                    .user(user)
                    .sessionTypeId(sessionReqDto.getSessionTypeId())
                    .name(sessionReqDto.getName())
                    .build();
        }else{
            return Session.builder()
                    .anniversary(sessionReqDto.getAnniversary())
                    .expireTime(sessionReqDto.getAnniversary().plusDays(7))
                    .user(user)
                    .sessionTypeId(sessionReqDto.getSessionTypeId())
                    .build();
        }

    }
    public static SessionResDto toDto(User user, Session session){
        // userId + name , sessionTypeId + anniversary 를 각 객체에서 받아와 파라미터를 줄여라
        return SessionResDto.builder()
                .userId(user.getUserId())
                .name(session.getName())
                .sessionId(session.getSessionId())
                .sessionTypeId(session.getSessionTypeId())
                .anniversary(session.getAnniversary())
                .build();
    }

}
