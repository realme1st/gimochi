package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeAuth;

import com.ssafy.db.entity.ChallengeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeAuthRepository extends JpaRepository<ChallengeAuth, Long> {


    Optional<ChallengeAuth> findByChallengeAuthId(Long challengeAuthId);

    Optional<ChallengeAuth> findByChallengeInfo(ChallengeInfo challengeInfo);
}