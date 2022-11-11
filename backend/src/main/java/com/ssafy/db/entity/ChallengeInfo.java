package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class ChallengeInfo {
    @Id
    @Column(name="challenge_info_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeInfoId;

    @JsonIgnore
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="challenge_id")
    private Challenge challenge;

    @Column(name="success_cnt")
    @ColumnDefault("0")
    private int successCnt;

//    @Column(name="my_rank")
//    @ColumnDefault("0")
//    private int myRank;

    @Column(name="winner_name")
    @ColumnDefault("''")
    private String winnerName;

    @OneToMany(mappedBy = "challengeInfo")
    private List<ChallengeAuth> challengeAuthsList = new ArrayList<>();



    public void addChallengeAuth(ChallengeAuth challengeAuth){
        this.challengeAuthsList.add(challengeAuth);

        if(challengeAuth.getChallengeInfo() !=this) { //무한루프 방지
            challengeAuth.setChallengeInfo(this);
        }

    }


    public void setUser(User user) {
        this.user = user;

        if(!user.getChallengeInfoList().contains(this)) {
            user.getChallengeInfoList().add(this);
        }
    }
    public void setChallenge(Challenge challenge){
        this.challenge =challenge;
        //무한 루프 주의
        if(!challenge.getChallengeInfoList().contains(this)){
            challenge.getChallengeInfoList().add(this);
        }
    }

    @Builder
    public ChallengeInfo(User user, Challenge challenge, int successCnt, String winnerName) {
        this.user = user;
        this.challenge = challenge;
        this.successCnt = successCnt;
        //this.myRank = myRank;
        this.winnerName = winnerName;
    }

    // 달성 횟수 증가
    public void successCntUp() {
        this.successCnt += 1;
    }
}
