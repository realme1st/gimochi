package com.ssafy.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Vote {
    @Id
    @Column(name="vote_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voteId;

    @Column(nullable = false,name="auth_user_id")
    private Long authUserId;

    @Column(nullable = false,name="vote_user_id")
    private Long voteUserId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="challenge_auth_id")
    private ChallengeAuth challengeAuth;

    public void setChallengeAuth(ChallengeAuth challengeAuth){
        this.challengeAuth = challengeAuth;
        if(!challengeAuth.getVoteList().contains(this)){
            challengeAuth.getVoteList().add(this);
        }
    }

    @Builder
    public Vote(Long voteId, Long authUserId, Long voteUserId, ChallengeAuth challengeAuth){
        this.voteId = voteId;
        this.authUserId = authUserId;
        this.voteUserId = voteUserId;
        this.challengeAuth = challengeAuth;
    }
}
