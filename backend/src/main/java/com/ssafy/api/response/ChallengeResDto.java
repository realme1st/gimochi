package com.ssafy.api.response;

import com.ssafy.api.request.ChallengeReqDto;
import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.User;
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

    public static Challenge createChallenge(ChallengeReqDto challengeReqDto) {
        return Challenge.builder()
                .challengeLeaderId(challengeReqDto.getChallengeLeaderId())
                .challengeLeaderName(challengeReqDto.getChallengeLeaderName())
                .challengeTitle(challengeReqDto.getChallengeTitle())
                .challengeDescription(challengeReqDto.getChallengeDescription())
                .challengeStartDate(challengeReqDto.getChallengeStartDate())
                .challengeEndDate(challengeReqDto.getChallengeEndDate())
                .challengeRewardType(challengeReqDto.getChallengeRewardType())
                .challengeRewardPoint(challengeReqDto.getChallengeRewardPoint())
                .challengeParticipantPoint(challengeReqDto.getChallengeParticipantPoint())
                .challengeActive(challengeReqDto.getChallengeActive())
                .build();
    }
    public static ChallengeResDto toDto(User user, Challenge challenge) {
        return ChallengeResDto.builder()
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
                .build();
    }

}
