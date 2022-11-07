package com.ssafy.db.entity;

import lombok.*;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Gifticon{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gifticonId;

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

    /* Session Message */
    @OneToOne(mappedBy = "session")
    private SessionMessage sessionMessages;

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getGifticonsList().contains(this)){
            user.getGifticonsList().add(this);
        }
    }
    @Builder
    public Gifticon(User user, LocalDate gifticonPeriod, String gifticonStore, boolean gifticonUsed, String gifticonPath) {
        this.user = user;
        this.gifticonPeriod = gifticonPeriod;
        this.gifticonStore = gifticonStore;
        this.gifticonUsed = gifticonUsed;
        this.gifticonPath = gifticonPath;
    }


}
