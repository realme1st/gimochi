package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeListInviteResDto {
    private String challengeTitle;
    private String challengeLeaderName;
    private Long challengeId;

    @Builder
    public ChallengeListInviteResDto(String challengeTitle, String challengeLeaderName, Long challengeId) {
        this.challengeTitle = challengeTitle;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeId = challengeId;
    }

}
