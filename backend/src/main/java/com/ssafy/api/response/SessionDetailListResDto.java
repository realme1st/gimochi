package com.ssafy.api.response;

import com.ssafy.db.entity.Session;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/* userId 기반 Session List 조회 결과 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionDetailListResDto {
    private List<SessionDetailResDto> sessionDetailResDtoList;

    @Builder
    public SessionDetailListResDto(List<SessionDetailResDto> sessionDetailResDtoList) {
        this.sessionDetailResDtoList = sessionDetailResDtoList;
    }

    public static List<SessionDetailResDto> toDtoList(List<Session> sessionList){
        List<SessionDetailResDto> sessionDetailResDtoList = new ArrayList<>();
        for(Session s : sessionList){
            sessionDetailResDtoList.add(
                    SessionDetailResDto.builder()
                            .sessionId(s.getSessionId())
                            .name(s.getName())
                            .expireTime(s.getExpireTime())
                            .anniversary(s.getAnniversary())
                            .sessionTypeId(s.getSessionTypeId())
                            .sessionMessageResDtoList(SessionMessageResDto.toDtoList(s.getSessionMessagesList()))
                            .build());
        }
        return sessionDetailResDtoList;
    }
}


