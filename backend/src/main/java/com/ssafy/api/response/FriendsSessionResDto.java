package com.ssafy.api.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendsSessionResDto {
    private Long userId;
    private List<SessionResDto> list;

    @Builder
    public FriendsSessionResDto(Long userId, List<SessionResDto> list) {
        this.userId = userId;
        this.list = list;
    }
}

