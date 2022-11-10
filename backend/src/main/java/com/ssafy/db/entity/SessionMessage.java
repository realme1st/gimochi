package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(nullable = false, name = "expire_time")
    private LocalDate expireTime;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sessionId")
    private Session session;

    // @OneToOne(mappedBy = "sessionMessage")
    @OneToOne
    @JoinColumn(name = "gifticonId")
    private Gifticon gifticon;


    public void setSession(Session session) {
        this.session = session;
        //무한 루프 주의
        if (!session.getSessionMessagesList().contains(this)) {
            session.getSessionMessagesList().add(this);
        }
    }

    public void setGifticon(Gifticon gifticon){
        this.gifticon = gifticon;
    }

    @Builder
    public SessionMessage(String nickname, String field, LocalDate expireTime, Session session) {
        this.nickname = nickname;
        this.field = field;
        this.session = session;
        this.expireTime = expireTime;
    }

    @Builder
    public SessionMessage(String nickname, String field, LocalDate expireTime, Session session, Gifticon gifticon) {
        this.nickname = nickname;
        this.field = field;
        this.session = session;
        this.gifticon = gifticon;
        this.expireTime = expireTime;
    }

}
