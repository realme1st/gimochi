package com.ssafy.db.repository;

import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.SessionType;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findChallengeByChallengeId(Long challengeId);
    //Optional<Challenge> findChallengeByChallengeUserId(Long challengeUserId);

}
