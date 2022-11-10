package com.ssafy.api.response;

import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.SessionMessage;
import com.ssafy.db.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/* SessionId 기반 Session 정보 조회 결과 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionDetailResDto {
    private Long sessionId;
    private String name;
    private LocalDate expireTime;
    private LocalDate anniversary;
    private Long sessionTypeId;
    private List<SessionMessageResDto> sessionMessageResDtoList;

    @Builder
    public SessionDetailResDto(Long sessionId, String name, LocalDate expireTime, LocalDate anniversary, Long sessionTypeId, List<SessionMessageResDto> sessionMessageResDtoList) {
        this.sessionId = sessionId;
        this.name = name;
        this.expireTime = expireTime;
        this.anniversary = anniversary;
        this.sessionTypeId = sessionTypeId;
        this.sessionMessageResDtoList = sessionMessageResDtoList;
    }

    public static SessionDetailResDto toDto(Session session, List<SessionMessage> mList){
        // userId + name , sessionTypeId + anniversary 를 각 객체에서 받아와 파라미터를 줄여라
        return SessionDetailResDto.builder()
                .sessionId(session.getSessionId())
                .name(session.getName())
                .sessionTypeId(session.getSessionTypeId())
                .expireTime(session.getExpireTime())
                .anniversary(session.getAnniversary())
                .sessionMessageResDtoList(SessionMessageResDto.toDtoList(mList))
                .build();
    }

}
