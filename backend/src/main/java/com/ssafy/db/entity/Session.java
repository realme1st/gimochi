package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new 막음
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sessionId;

    private String name;
    private String url;
    @Column(nullable = false, name = "create_time")
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDate anniversary;

    /* Session Message */
    @OneToMany(mappedBy = "session")
    private List<SessionMessage> sessionMessagesList = new ArrayList<>();

    /* User */
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "userId")
    private User user;

    /* Type */
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "sessionTypeId")
    private SessionType sessionType;

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getSessionsList().contains(this)){
            user.getSessionsList().add(this);
        }
    }

    public void setSessionType(SessionType sessionType){
        this.sessionType = sessionType;
        //무한 루프 주의
        if(!sessionType.getSessionsList().contains(this)){
            sessionType.getSessionsList().add(this);
        }
    }

}
