package com.ssafy.api.response;

import com.ssafy.db.entity.Challenge;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeResDto {
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

    @Builder
    public ChallengeResDto(Long challengeLeaderId, String challengeLeaderName, String challengeTitle, String challengeDescription, LocalDate challengeStartDate, LocalDate challengeEndDate, int challengeRewardType, int challengeRewardPoint, int challengeParticipantPoint, int challengeActive) {
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
    }

    public static Challenge createChallenge(ChallengeResDto challengeResDto) {
        return Challenge.builder()
                .challengeLeaderId(challengeResDto.getChallengeLeaderId())
                .challengeLeaderName(challengeResDto.getChallengeLeaderName())
                .challengeTitle(challengeResDto.getChallengeTitle())
                .challengeDescription(challengeResDto.getChallengeDescription())
                .challengeStartDate(challengeResDto.getChallengeStartDate())
                .challengeEndDate(challengeResDto.getChallengeEndDate())
                .challengeRewardType(challengeResDto.getChallengeRewardType())
                .challengeRewardPoint(challengeResDto.getChallengeRewardPoint())
                .challengeParticipantPoint(challengeResDto.getChallengeParticipantPoint())
                .challengeActive(challengeResDto.getChallengeActive())
                .build();
    }

}
