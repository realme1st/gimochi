package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
@ToString(callSuper=true)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = true)
    private String userProfile;
    @Column(nullable = false)
    private int userPoint;
    @Column(nullable = true)
    private String userNickname;
    @Column(nullable = false)
    private Date userBirthday;
    @Column(nullable = false)
    private String userSocialToken;
    @Column(nullable = false)
    private String userFbToken;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Gifticon> gifticonsList = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<ChallengeInvite> challengeInvitesList = new ArrayList<>();

    public void addGifticon(Gifticon gifticon){
        this.gifticonsList.add(gifticon);

        if(gifticon.getUser() !=this) { //무한루프 방지
            gifticon.setUser(this);
        }

    }

    public void addChallengeInvite(ChallengeInvite challengeInvite){
        this.challengeInvitesList.add(challengeInvite);

        if(challengeInvite.getUser() !=this) { //무한루프 방지
            challengeInvite.setUser(this);
        }

    }



    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Session> sessionsList = new ArrayList<>();



}
