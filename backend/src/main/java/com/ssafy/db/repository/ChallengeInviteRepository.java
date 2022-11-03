package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeInvite;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChallengeInviteRepository extends JpaRepository<ChallengeInvite, Long> {
    List<ChallengeInvite> findChallengeInvitesByUser(User user);

    @Query("select c from ChallengeInvite c where c.user.userId = ?1")
    List<ChallengeInvite> findAllByChallengeInviteUserId(Long userId);

    Optional<ChallengeInvite> findByChallengeInviteId(Long challengeInviteId);
}
