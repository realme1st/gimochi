package com.ssafy.api.dto;

import lombok.Getter;

/* challengeAuth 를 업데이트 할 떄 필요한 request dto*/
@Getter
public class UpdateChallengeAuthReqDto {
    private Long challengeAuthId;   // 투표하려는 챌린지 Id
    private Long authUserId; // challengeAuth의 주인
    private Long voteUserId; // 투표 하는 사람
}