package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeAuth;
import com.ssafy.db.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
