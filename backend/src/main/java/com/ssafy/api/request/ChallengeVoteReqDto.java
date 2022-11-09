package com.ssafy.api.request;

import lombok.Getter;

@Getter
public class ChallengeVoteReqDto {
    Long challengeInfoId;
    Long challengeAuthId;
    Long authUserId;
    Long voteUserId;
}
