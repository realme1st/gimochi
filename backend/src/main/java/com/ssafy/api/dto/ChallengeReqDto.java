package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChallengeReqDto {
    @ApiModelProperty(name="챌린지 제목", example="1일 1커밋")
    String challengeTitle;
    @ApiModelProperty(name="챌린지 만든 유저", example="1")
    Long challengeUserId;

    @ApiModelProperty(name="챌린지 내용", example="매일매일 커밋하기")
    String challengeDescription;

    @ApiModelProperty(name="챌린지 참가 인원 수", example="4")
    Long challengeParticipant;

    // 프론트에서 시간 처리 방식을 정함(String or localDateTime)
    @ApiModelProperty(name="챌린지 시작 시간", example="2022.10.28 13:00:00")
    String challengeStartTime;

    @ApiModelProperty(name="챌린지 종료 시간", example="2022.10.30 13:00:00")
    String challengeEndTime;

    @ApiModelProperty(name="챌린지 보상 타입", example="1")
    int challengeRewardType;
}
