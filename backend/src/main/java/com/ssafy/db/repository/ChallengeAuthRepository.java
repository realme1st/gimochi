package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeAuth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeAuthRepository extends JpaRepository<ChallengeAuth, Long> {

}
