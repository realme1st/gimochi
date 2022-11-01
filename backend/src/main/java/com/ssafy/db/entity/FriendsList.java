package com.ssafy.db.entity;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class FriendsList{
    @Id
    @Column(name = "friends_list_id")
    private Long friendsListId;

    // 팔로워 (나)
    @Column(name = "follower_id", nullable = false)
    private Long followerUserId;

    // 팔로잉 (상대방 아이디)
    @Column(name = "following_id", nullable = false)
    private Long followingUserId;
}
