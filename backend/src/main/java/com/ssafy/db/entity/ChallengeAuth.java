package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
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

    @Column(name="user_id")
    private int userId;

    @Column(name="challenge_path")
    private String challengePath;

    @Column(name="vote_cnt")
    private int voteCnt;

    @Column(name="is_confirm")
    private boolean isConfirm;

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
}
