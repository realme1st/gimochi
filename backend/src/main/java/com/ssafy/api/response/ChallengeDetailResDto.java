package com.ssafy.api.response;

import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.ChallengeInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ChallengeDetailResDto {
    private Long challengeId;

    private Long challengeLeaderId;

    private String challengeLeaderName;

    private String challengeTitle;

    private String challengeDescription;

    private LocalDate challengeStartDate;

    private LocalDate challengeEndDate;

    private int challengeRewardType;

    private int challengeRewardPoint;

    private int challengeParticipantPoint;

    private int challengeActive;

    private List<ChallengeInfo> challengeInfoList;

    @Builder
    public ChallengeDetailResDto(Long challengeId, Long challengeLeaderId, String challengeLeaderName,
                                 String challengeTitle, String challengeDescription, LocalDate challengeStartDate,
                                 LocalDate challengeEndDate, int challengeRewardType, int challengeRewardPoint, int challengeParticipantPoint, int challengeActive
            , List<ChallengeInfo> challengeInfoList) {
        this.challengeId = challengeId;
        this.challengeLeaderId = challengeLeaderId;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeTitle = challengeTitle;
        this.challengeDescription = challengeDescription;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challengeRewardType = challengeRewardType;
        this.challengeRewardPoint = challengeRewardPoint;
        this.challengeParticipantPoint = challengeParticipantPoint;
        this.challengeActive = challengeActive;
        this.challengeInfoList = challengeInfoList;
    }


    public static ChallengeDetailResDto toDto(Challenge challenge) {
        return ChallengeDetailResDto.builder()
                .challengeId(challenge.getChallengeId())
                .challengeLeaderId(challenge.getChallengeLeaderId())
                .challengeLeaderName(challenge.getChallengeLeaderName())
                .challengeTitle(challenge.getChallengeTitle())
                .challengeDescription(challenge.getChallengeDescription())
                .challengeStartDate(challenge.getChallengeStartDate())
                .challengeEndDate(challenge.getChallengeEndDate())
                .challengeRewardType(challenge.getChallengeRewardType())
                .challengeRewardPoint(challenge.getChallengeRewardPoint())
                .challengeParticipantPoint(challenge.getChallengeParticipantPoint())
                .challengeActive(challenge.getChallengeActive())
                .challengeInfoList(challenge.getChallengeInfoList())
                .build();
    }
}
