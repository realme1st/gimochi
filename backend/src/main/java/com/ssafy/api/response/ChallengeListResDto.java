package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ChallengeListResDto {
    private Long challengeId;

    private int successCnt;

    private String challengeTitle;

    private String challengeLeaderName;

    private int challengeRewardType;

    private int  challengeActive;

    private LocalDate challengeStartDate;

    private LocalDate challengeEndDate;

    @Builder
    public ChallengeListResDto(Long challengeId, int successCnt, String challengeTitle,
                               String challengeLeaderName, int challengeRewardType, int challengeActive,
                               LocalDate challengeStartDate, LocalDate challengeEndDate) {
        this.challengeId = challengeId;
        this.successCnt = successCnt;
        this.challengeTitle = challengeTitle;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeRewardType = challengeRewardType;
        this.challengeActive = challengeActive;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;

    }
}
