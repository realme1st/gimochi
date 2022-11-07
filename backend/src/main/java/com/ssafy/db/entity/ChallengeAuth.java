package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Column(nullable = false,name="challenge_date")
    private LocalDate challengeDate;

    @Column(name="is_confirm")
    private int isConfirm;

    @Column(name="challenger_cnt")
    private int challengerCnt;

    @OneToMany(mappedBy = "challengeAuth")
    private List<Vote> voteList = new ArrayList<>();


    public void addVote(Vote vote){
        this.voteList.add(vote);

        if(vote.getChallengeAuth() !=this) { //무한루프 방지
            vote.setChallengeAuth(this);
        }

    }

    public void setChallengeInfo(ChallengeInfo challengeinfo){
        this.challengeInfo =challengeinfo;
        //무한 루프 주의
        if(!challengeinfo.getChallengeAuthsList().contains(this)){
            challengeinfo.getChallengeAuthsList().add(this);
        }
    }

    @Builder
    public ChallengeAuth(ChallengeInfo challengeInfo,String challengePath, int voteCnt, LocalDate challengeDate,int isConfirm,int challengerCnt){
        this.challengeInfo = challengeInfo;
        this.challengePath = challengePath;
        this.voteCnt = voteCnt;
        this.isConfirm = isConfirm;
        this.challengeDate = challengeDate;
        this.challengerCnt = challengerCnt;
    }

    // 투표수 증가
    public void voteCntUp() {
        this.voteCnt += 1; 
    }
}
