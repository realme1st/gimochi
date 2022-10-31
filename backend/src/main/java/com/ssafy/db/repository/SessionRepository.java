package com.ssafy.db.repository;

import com.ssafy.db.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<List<Session>> findAllByUserUserId(Long userId);
}
