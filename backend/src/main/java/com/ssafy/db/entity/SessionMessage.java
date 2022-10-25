package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new() 막음
public class SessionMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sessionMessageId;

    @Column
    private String nickname;
    @Column(nullable = false)
    private String field;
    @Column(nullable = false, name = "create_time")
    private LocalDateTime createTime;


    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "sessionId")
    private Session session;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "gifticonId")
    private Gifticon gifticon;


    public void setSession(Session session){
        this.session = session;
        //무한 루프 주의
        if(!session.getSessionMessagesList().contains(this)){
            session.getSessionMessagesList().add(this);
        }
    }

    public void setGifticon(Gifticon gifticon){
        this.gifticon = gifticon;
        //무한 루프 주의
        if(!gifticon.getSessionMessagesList().contains(this)){
            gifticon.getSessionMessagesList().add(this);
        }
    }


}
