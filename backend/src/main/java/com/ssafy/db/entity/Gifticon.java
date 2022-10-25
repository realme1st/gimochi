package com.ssafy.db.entity;

import lombok.*;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Gifticon{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long gifticonId;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @Column(nullable = false)
    private Date gifticonPeriod;
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


}
