package com.ssafy.db.repository;

import com.ssafy.api.response.SessionMessageResDto;
import com.ssafy.db.entity.SessionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionMessageRepository extends JpaRepository<SessionMessage, Long> {

    Optional<SessionMessage> findBySessionMessageId(Long sessionMessageId);
     // sessionID로 메세지 리스트 가져온다
    @Query(value = "select * from session_message where session_id = ?1", nativeQuery = true)
    Optional<List<SessionMessage>> findMessageList(Long sessionId);

}
