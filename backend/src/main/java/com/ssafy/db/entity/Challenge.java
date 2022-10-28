package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Challenge {
    @Id
    @Column(name="challenge_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeId;

    @Column(nullable = false,name="challenge_user_id")
    private Long challengeUserId;

    @Column(nullable = false,name="challenge_title")
    private String challengeTitle;

    @Column(nullable = false,name="challenge_description")
    private String challengeDescription;

    @Column(nullable = false,name="challenge_participant")
    private Long challengeParticipant;

    @Column(nullable = false,name="challenge_start_time")
    private String challengeStartTime;

    @Column(nullable = false,name="challenge_end_time")
    private String challengeEndTime;


    @OneToMany(mappedBy = "challenge")
    private List<ChallengeInfo> challengeInfoList = new ArrayList<>();

    public void addChallengeInfo(ChallengeInfo challengeinfo){
        this.challengeInfoList.add(challengeinfo);

        if(challengeinfo.getChallenge() !=this) { //무한루프 방지
            challengeinfo.setChallenge(this);
        }

    }

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeReward> challengeRewardList = new ArrayList<>();

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeInvite> challengeInviteList = new ArrayList<>();

    @Builder
    public Challenge(Long challengeUserId, String challengeTitle, String challengeDescription, Long challengeParticipant, String challengeStartTime, String challengeEndTime) {
        this.challengeUserId = challengeUserId;
        this.challengeTitle = challengeTitle;
        this.challengeDescription = challengeDescription;
        this.challengeParticipant = challengeParticipant;
        this.challengeStartTime = challengeStartTime;
        this.challengeEndTime = challengeEndTime;
    }




    public void addChallengeReward(ChallengeReward challengeReward){
        this.challengeRewardList.add(challengeReward);

        if(challengeReward.getChallenge() !=this) { //무한루프 방지
            challengeReward.setChallenge(this);
        }

    }
    public void addChallengeInvite(ChallengeInvite challengeInvite){
        this.challengeInviteList.add(challengeInvite);

        if(challengeInvite.getChallenge() !=this) { //무한루프 방지
            challengeInvite.setChallenge(this);
        }

    }
}
