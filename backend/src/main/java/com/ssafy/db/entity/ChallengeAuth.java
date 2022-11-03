package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class ChallengeAuth {
    @Id
    @Column(name="challenge_auth_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeAuthId;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="challenge_info_id")
    private ChallengeInfo challengeInfo;

    @Column(name="auth_user_id")
    private Long authUserId;

    @Column(name="challenge_path")
    private String challengePath;

    @Column(name="vote_cnt")
    private int voteCnt;

    @Column(name="is_confirm")
    private int isConfirm;

    @Column(name="challenge_date")
    @Temporal(TemporalType.TIME)
    private Date challengeDate;


    public void setChallengeInfo(ChallengeInfo challengeinfo){
        this.challengeInfo =challengeinfo;
        //무한 루프 주의
        if(!challengeinfo.getChallengeAuthsList().contains(this)){
            challengeinfo.getChallengeAuthsList().add(this);
        }
    }

    @Builder
    public ChallengeAuth(ChallengeInfo challengeInfo, Long authUserId, String challengePath, int voteCnt, int isConfirm, Date challengeDate){
        this.challengeInfo = challengeInfo;
        this.authUserId = authUserId;
        this.challengePath = challengePath;
        this.voteCnt = voteCnt;
        this.isConfirm = isConfirm;
        this.challengeDate = challengeDate;
    }



}
