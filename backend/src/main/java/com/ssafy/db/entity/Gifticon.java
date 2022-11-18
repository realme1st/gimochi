package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Gifticon{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gifticonId;

    @JsonIgnore
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private LocalDate gifticonPeriod;
    @Column(nullable = false)
    private String gifticonStore;
    @Column(nullable = false)
    private boolean gifticonUsed;
    @Column(nullable = false)
    private String gifticonPath;

    @Column(nullable = false)
    private String gifticonCode;

    /* Session Message */
    @JsonIgnore
    @OneToOne(mappedBy = "gifticon")
    private SessionMessage sessionMessage;
    @JsonIgnore
    @OneToMany(mappedBy = "gifticon")
    private List<ChallengeReward> challengeRewardList = new ArrayList<>();

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getGifticonsList().contains(this)){
            user.getGifticonsList().add(this);
        }
    }
    @Builder
    public Gifticon(User user, LocalDate gifticonPeriod, String gifticonStore, boolean gifticonUsed,
                    String gifticonPath, String gifticonCode) {
        this.user = user;
        this.gifticonPeriod = gifticonPeriod;
        this.gifticonStore = gifticonStore;
        this.gifticonUsed = gifticonUsed;
        this.gifticonPath = gifticonPath;
        this.gifticonCode = gifticonCode;
    }

    public void changeGifticonPath(String path) {
        this.gifticonPath = path;
    }

    public void changeGifticonUser(User user) {
        this.user = user;
    }

    public void changeGifticonUsed() {
        this.gifticonUsed = !this.gifticonUsed;
    }

    public void changeGifticonStorePeriod(String store, LocalDate period) {
        this.gifticonStore = store;
        this.gifticonPeriod = period;
    }

    public void setSessionMessage(SessionMessage sessionMessage) {
        this.sessionMessage = sessionMessage;
        //무한 루프 주의
        if (!sessionMessage.getGifticon().equals(this)) {
            sessionMessage.setGifticon(this);
        }
    }

    public void addChallengeReward(ChallengeReward challengeReward){
        this.challengeRewardList.add(challengeReward);

        if(challengeReward.getGifticon() !=this) { //무한루프 방지
            challengeReward.setGifticon(this);
        }

    }

}
