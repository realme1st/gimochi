package com.ssafy.db.repository;

import com.ssafy.api.dto.AuthTotalCnt;
import com.ssafy.db.entity.ChallengeAuth;

import com.ssafy.db.entity.ChallengeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeAuthRepository extends JpaRepository<ChallengeAuth, Long> {


    Optional<ChallengeAuth> findByChallengeAuthId(Long challengeAuthId);

    //challengeId에 해당하는 유저들 ranking

    //challengInfoId 에 해당하는 전체 count
    @Query(value = "select count(ch.challenge_auth_id) as totalCnt from challenge_auth ch where ch.challenge_info_id = ?1",nativeQuery = true)
    Optional<AuthTotalCnt> findTotalCntByChallengeInfo(Long challengeInfoId);
}