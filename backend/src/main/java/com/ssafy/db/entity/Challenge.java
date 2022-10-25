package com.ssafy.db.entity;

import lombok.AccessLevel;
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
    private int challengeParticipant;

    @Column(nullable = false,name="challenge_start_time")
    @Temporal(TemporalType.TIME)
    private Date challengeStartTime;

    @Column(nullable = false,name="challenge_end_time")
    @Temporal(TemporalType.TIME)
    private Date challengeEndTime;


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
