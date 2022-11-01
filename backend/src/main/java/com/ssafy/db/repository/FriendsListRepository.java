package com.ssafy.db.repository;

import com.ssafy.db.entity.FriendsList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsListRepository extends JpaRepository<FriendsList, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    Optional<List<FriendsList>> findAllByFollowerId(Long followerId);


    Optional<List<FriendsList>> findAllByFollowingId(Long userId);

    void deleteByFollowerIdAndFollowingId(Long followerUserId, Long followingUserId);
}
