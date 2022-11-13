package com.ssafy.api.request;

import lombok.Getter;

@Getter
public class ChallengeVoteReqDto {
    private Long challengeInfoId;
    private Long challengeAuthId;
    private Long authUserId;
    private Long voteUserId;
}
