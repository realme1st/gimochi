package com.ssafy.api.response;

import com.ssafy.db.entity.ChallengeAuth;
import com.ssafy.db.entity.ChallengeInfo;
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

    private int challenger_cnt;


    @Builder
    public ChallengeListResDto(Long challengeId, int successCnt, String challengeTitle,
                               String challengeLeaderName, int challengeRewardType, int challengeActive,
                               LocalDate challengeStartDate, LocalDate challengeEndDate,
                               int challenger_cnt) {
        this.challengeId = challengeId;
        this.successCnt = successCnt;
        this.challengeTitle = challengeTitle;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeRewardType = challengeRewardType;
        this.challengeActive = challengeActive;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challenger_cnt = challenger_cnt;

    }

    public static ChallengeListResDto toDto(ChallengeInfo challengeInfo) {
        return ChallengeListResDto.builder()
                .challengeId(challengeInfo.getChallenge().getChallengeId())
                .challengeTitle(challengeInfo.getChallenge().getChallengeTitle())
                .successCnt(challengeInfo.getSuccessCnt())
                .challengeActive(challengeInfo.getChallenge().getChallengeActive())
                .challengeLeaderName(challengeInfo.getChallenge().getChallengeLeaderName())
                .challengeStartDate(challengeInfo.getChallenge().getChallengeStartDate())
                .challengeEndDate(challengeInfo.getChallenge().getChallengeEndDate())
                .challengeRewardType(challengeInfo.getChallenge().getChallengeRewardType())
                .challenger_cnt(challengeInfo.getChallenge().getChallengeInfoList().size())
                .build();
    }
}
