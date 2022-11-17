package com.ssafy.db.repository;

import com.ssafy.api.dto.RankInterface;
import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.ChallengeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeInfoRepository extends JpaRepository<ChallengeInfo, Long> {

    @Query("select ch from ChallengeInfo ch join fetch ch.challenge where ch.challenge.challengeId = :challengeId")
    Optional<List<ChallengeInfo>> findUserListByChallengeId(Long challengeId);

    @Query("select ch from ChallengeInfo ch join fetch ch.challenge where ch.user.userId = :userId")
    Optional<List<ChallengeInfo>> findChallengeListByUserId(Long userId);

    ChallengeInfo findByChallenge(Challenge challenge);


    Optional<ChallengeInfo> findByChallengeInfoId(Long challengeInfoId);

    //challengeId에 속한 userId 수
    @Query("select count(ch.user.userId) from ChallengeInfo ch where ch.challenge.challengeId = :challengeId")
    int findChallegerCntByChallengeId(Long challengeId);

    //challengeId에 해당하는 유저들 ranking
    @Query(value = "SELECT user_id AS userId, RANK() OVER(PARTITION BY challenge_id ORDER BY success_cnt desc) AS myRank "+
            "FROM challenge_info " +
            "WHERE challenge_id = ?1", nativeQuery = true)
    Optional<List<RankInterface>> findChallengeInfoListByChallengeId(Long challengeId);

//challengeId와 userId에 해당하는 challengeInfo
    @Query("select ch from ChallengeInfo ch where ch.challenge.challengeId = :challengeId and ch.user.userId = :userId")
    Optional<ChallengeInfo> findByChallengeIdUserId(Long challengeId,Long userId);
}
