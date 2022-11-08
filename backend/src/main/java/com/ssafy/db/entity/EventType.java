package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new 막음
public class EventType {
    @Id
    @Column(name = "event_type_id")
    private Long eventTypeId;

    @Column(nullable = false, name = "event_type")
    private String eventType;

    @OneToMany(mappedBy = "eventType")
    private List<EventInfo> eventInfosList = new ArrayList<>();

}
