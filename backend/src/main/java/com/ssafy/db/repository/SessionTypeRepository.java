package com.ssafy.db.repository;

import com.ssafy.db.entity.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    Optional<SessionType> findSessionTypeBySessionTypeId(Long sessionTypeId);

}
