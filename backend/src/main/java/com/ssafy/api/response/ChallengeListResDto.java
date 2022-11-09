package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeListResDto {
    private Long challengeId;

    private int successCnt;

    private String challengeName;

    @Builder
    public ChallengeListResDto(Long challengeId, int successCnt, String challengeName) {
        this.challengeId = challengeId;
        this.successCnt = successCnt;
        this.challengeName = challengeName;
    }
}
