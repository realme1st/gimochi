package com.ssafy.api.response;

import com.ssafy.api.request.SessionMessageReqDto;
import com.ssafy.db.entity.Gifticon;
import com.ssafy.db.entity.SessionMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionMessageResDto {
    private Long sessionMessageId;
    private String nickname;
    private String field;
    private LocalDate expireTime;

    private String gifticonStore;
    private int messageType;

    @Builder
    public SessionMessageResDto(Long sessionMessageId, String nickname, String field, LocalDate expireTime, int messageType, String gifticonStore) {
        this.sessionMessageId = sessionMessageId;
        this.nickname = nickname;
        this.field = field;
        this.expireTime = expireTime;
        this.messageType = messageType;
        this.gifticonStore = gifticonStore;
    }

    public static SessionMessage toDto(SessionMessageReqDto reqDto){
        return SessionMessage.builder()
                .nickname(reqDto.getNickname())
                .field(reqDto.getField())
                .expireTime(LocalDate.now().plusDays(7))
                .messageType(reqDto.getMessageType())
                .build();
    }

    public static SessionMessageResDto of(SessionMessage sessionMessage){
        return SessionMessageResDto.builder()
                .sessionMessageId(sessionMessage.getSessionMessageId())
                .nickname(sessionMessage.getNickname())
                .field(sessionMessage.getField())
                .expireTime(sessionMessage.getExpireTime())
                .messageType(sessionMessage.getMessageType())
                .gifticonStore(sessionMessage.getGifticon().getGifticonStore())
                .build();
    }

    public static List<SessionMessageResDto> toDtoList(List<SessionMessage> mList){
        List<SessionMessageResDto> sessionMessageResDtoList =new ArrayList<>();
        for(SessionMessage m : mList){
            sessionMessageResDtoList.add(
                    SessionMessageResDto.builder()
                    .messageType(m.getMessageType())
                    .sessionMessageId(m.getSessionMessageId())
                    .nickname(m.getNickname())
                    .field(m.getField())
                    .expireTime(m.getExpireTime())
                            .gifticonStore(m.getGifticon().getGifticonStore())
                    .build());
        }
        return sessionMessageResDtoList;
    }

}
