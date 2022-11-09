package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChallengeReqDto {
    @ApiModelProperty(name="챌린지 제목", example="1일 1커밋")
    String challengeTitle;
    @ApiModelProperty(name="챌린지 만든 유저", example="1")
    Long challengeLeaderId;

    @ApiModelProperty(name="챌린지 내용", example="매일매일 커밋하기")
    String challengeDescription;

    @ApiModelProperty(name="챌린지 방장이름", example="홍길동")
    String challengeLeaderName;

    // 프론트에서 시간 처리 방식을 정함(String or localDateTime)
    @ApiModelProperty(name="챌린지 시작 시간", example="2022-10-28")
    LocalDate challengeStartTime;

    @ApiModelProperty(name="챌린지 종료 시간", example="2022-10-30")
    LocalDate challengeEndTime;

    @ApiModelProperty(name="챌린지 보상 타입", example="1")
    int challengeRewardType;

    @ApiModelProperty(name="챌린지 성공 총 보상 포인트", example="0")
    int challengeRewardPoint;

    @ApiModelProperty(name="챌린지 참가비 포인트", example="10")
    int challengeParticipantPoint;
}
