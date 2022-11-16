package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class ChallengeInvite {
    @Id
    @Column(name="challenge_invite_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeInviteId;

    @JsonIgnore
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(name="invite_status")

    private int inviteStatus;
    @Builder
    public ChallengeInvite(User user, Challenge challenge,int inviteStatus) {
        this.user = user;
        this.challenge = challenge;
        this.inviteStatus = inviteStatus;
    }

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getChallengeInvitesList().contains(this)){
            user.getChallengeInvitesList().add(this);
        }
    }

    public void setChallenge(Challenge challenge){
        this.challenge =challenge;
        //무한 루프 주의
        if(!user.getChallengeInvitesList().contains(this)){
            user.getChallengeInvitesList().add(this);
        }
    }




}
