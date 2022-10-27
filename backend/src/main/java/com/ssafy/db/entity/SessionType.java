package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new 막음
public class SessionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sessionTypeId;

    @Column(nullable = false)
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "sessionType")
    private List<Session> sessionsList = new ArrayList<>();
}
