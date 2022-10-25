package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new 막음
@Getter
public class EventInfo {
    @Id
    @Column(name = "event_id")
    private Long eventInfoId;

    @Column(nullable = false, name = "event_name")
    private String eventTitle;
    @Column(nullable = false, name = "event_start_date")
    private LocalDate startDate;
    @Column(nullable = false, name = "event_end_date")
    private LocalDate endDate;
    @Column(nullable = false, name = "person_limit")
    private Integer personLimit;
    @Column(nullable = false, name = "winner_cnt")
    private Integer winnerCnt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    public void setEventType(EventType eventType){
        this.eventType = eventType;
        if(!eventType.getEventInfosList().contains(this)){
            eventType.getEventInfosList().add(this);
        }
    }

}
