package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class RewardInfo {
    @Id
    @Column(name = "challenge_reward_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeRewardId;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challengeId")
    private Challenge challenge;

    @Column(nullable = false, name = "challenge_reward_type")
    private Long challengeRewardType;

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
        //무한 루프 주의
        if (!challenge.getRewardInfoList().contains(this)) {
            challenge.getRewardInfoList().add(this);
        }
    }

    @Builder
    public RewardInfo(Challenge challenge, Long challengeRewardType) {
        this.challenge = challenge;
        this.challengeRewardType = challengeRewardType;
    }
}