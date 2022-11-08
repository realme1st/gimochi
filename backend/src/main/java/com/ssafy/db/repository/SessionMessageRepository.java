package com.ssafy.db.repository;

import com.ssafy.db.entity.SessionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionMessageRepository extends JpaRepository<SessionMessage, Long> {

    Optional<SessionMessage> findBySessionMessageId(Long sessionMessageId);
}
