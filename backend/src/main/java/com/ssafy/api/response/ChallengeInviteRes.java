package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeInviteRes {
    private String challengeTitle;
    private String challengeLeaderName;
    private Long challengeId;

    @Builder
    public ChallengeInviteRes(String challengeTitle, String challengeLeaderName, Long challengeId) {
        this.challengeTitle = challengeTitle;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeId = challengeId;
    }

}
