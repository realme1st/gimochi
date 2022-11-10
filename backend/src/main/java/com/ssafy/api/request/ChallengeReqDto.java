package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChallengeReqDto {
    @ApiModelProperty(name="챌린지 제목", example="1일 1커밋")
    private String challengeTitle;
    @ApiModelProperty(name="챌린지 만든 유저", example="1")
    private Long challengeLeaderId;

    @ApiModelProperty(name="챌린지 내용", example="매일매일 커밋하기")
    private String challengeDescription;

    @ApiModelProperty(name="챌린지 방장이름", example="홍길동")
    private String challengeLeaderName;

    // 프론트에서 시간 처리 방식을 정함(String or localDateTime)
    @ApiModelProperty(name="챌린지 시작 날짜", example="2021-10-28")
    private LocalDate challengeStartDate;

    @ApiModelProperty(name="챌린지 종료 시간", example="2022-10-30")
    private LocalDate challengeEndDate;

    @ApiModelProperty(name="챌린지 보상 타입", example="1")
    private int challengeRewardType;

    @ApiModelProperty(name="챌린지 성공 총 보상 포인트", example="0")
    private int challengeRewardPoint;

    @ApiModelProperty(name="챌린지 참가비 포인트", example="10")
    private int challengeParticipantPoint;

    @ApiModelProperty(name="챌린지 활성화 여부", example="0")
    private int challengeActive;
}
