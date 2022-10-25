package com.ssafy.db.entity;

import lombok.*;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new User막음
public class Gifticon{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gifticonId;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(nullable = false)
    private Date gifticonPeriod;
    @Column(nullable = false)
    private String gifticonStore;
    @Column(nullable = false)
    private boolean gifticonUsed;
    @Column(nullable = false)
    private String gifticonPath;

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getGifticonsList().contains(this)){
            user.getGifticonsList().add(this);
        }
    }


}
