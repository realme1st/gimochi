package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRewardRepository extends JpaRepository<ChallengeReward, Long> {
    Optional<ChallengeReward> findByChallengeChallengeId(Long challengeId);
}
