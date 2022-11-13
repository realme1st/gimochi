package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Challenge {
    @Id
    @Column(name="challenge_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeId;

    @Column(nullable = false,name="challenge_leader_id")
    private Long challengeLeaderId;

    @Column(nullable=false,name="challenge_leader_name")
    private String challengeLeaderName;

    @Column(nullable = false,name="challenge_title")
    private String challengeTitle;

    @Column(nullable = false,name="challenge_description")
    private String challengeDescription;

    @Column(nullable = false,name="challenge_start_date")
    private LocalDate challengeStartDate;
    @Column(nullable = false,name="challenge_end_date")
    private LocalDate challengeEndDate;

    @Column(nullable = false,name="challenge_reward_type")
    private int challengeRewardType;

    @Column(nullable = false,name="challenge_reward_point")
    private int challengeRewardPoint;

    @Column(nullable = false,name="challenge_participant_point")
    private int challengeParticipantPoint;

    @Column(nullable = false,name="challenge_active")
    private int challengeActive;

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeInfo> challengeInfoList = new ArrayList<>();

    public void addChallengeInfo(ChallengeInfo challengeinfo){
        this.challengeInfoList.add(challengeinfo);

        if(challengeinfo.getChallenge() !=this) { //무한루프 방지
            challengeinfo.setChallenge(this);
        }

    }

    @OneToMany(mappedBy = "challenge")
    private List<RewardInfo> rewardInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeInvite> challengeInviteList = new ArrayList<>();

    @Builder
    public Challenge(Long challengeLeaderId, String challengeTitle, String challengeDescription, LocalDate challengeStartDate,
                     LocalDate challengeEndDate, int challengeRewardType,String challengeLeaderName,int challengeRewardPoint,int challengeParticipantPoint
                     ,int challengeActive) {
        this.challengeLeaderId = challengeLeaderId;
        this.challengeTitle = challengeTitle;
        this.challengeDescription = challengeDescription;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challengeRewardType = challengeRewardType;
        this.challengeLeaderName = challengeLeaderName;
        this.challengeRewardPoint = challengeRewardPoint;
        this.challengeParticipantPoint = challengeParticipantPoint;
        this.challengeActive = challengeActive;
    }




    public void addChallengeReward(RewardInfo rewardInfo){
        this.rewardInfoList.add(rewardInfo);

        if(rewardInfo.getChallenge() !=this) { //무한루프 방지
            rewardInfo.setChallenge(this);
        }

    }
    public void addChallengeInvite(ChallengeInvite challengeInvite){
        this.challengeInviteList.add(challengeInvite);

        if(challengeInvite.getChallenge() !=this) { //무한루프 방지
            challengeInvite.setChallenge(this);
        }

    }

    public int changeRewardPoint(int challengeRewardPoint){
        this.challengeRewardPoint=challengeRewardPoint+getChallengeParticipantPoint();
        return this.challengeRewardPoint;
    }

    public void changeActive(int challengeActive){
        this.challengeActive=challengeActive;
    }


}

