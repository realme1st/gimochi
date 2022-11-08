package com.ssafy.db.repository;

import com.ssafy.db.entity.RewardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRewardRepository extends JpaRepository<RewardInfo, Long> {
    Optional<RewardInfo> findByChallengeChallengeId(Long challengeId);
}
