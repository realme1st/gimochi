package com.ssafy.db.repository;

import com.ssafy.db.entity.FriendsList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsListRepository extends JpaRepository<FriendsList, Long> {

    boolean existsByFollowerUserIdAndFollowingUserId(Long followerUserId, Long followingUserId);
}
