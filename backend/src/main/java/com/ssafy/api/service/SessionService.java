package com.ssafy.api.service;

import com.ssafy.api.dto.SessionMessageReqDto;
import com.ssafy.api.dto.SessionReqDto;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.SessionMessage;
import com.ssafy.db.entity.SessionType;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.SessionMessageRepository;
import com.ssafy.db.repository.SessionRepository;
import com.ssafy.db.repository.SessionTypeRepository;
import com.ssafy.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service("SessionService")
@Transactional(readOnly = true)
@Slf4j
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionTypeRepository sessionTypeRepository;

    @Autowired
    private SessionMessageRepository sessionMessageRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private gifticonService gifticonService;

    /*
     * description: 세션 생성
     * return: 생성된 세션
     * author: 김다은
     * */
    @Transactional
    public Session createSession(SessionReqDto reqDto) {
        SessionType sessionType = sessionTypeRepository.findSessionTypeBySessionTypeId(reqDto.getSessionTypeId())
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_TYPE_NOT_FOUND));

        User user = userRepository.findByUserId(reqDto.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Session session = Session.builder()
                .user(user)
                .name(reqDto.getName())
                .sessionType(sessionType)
                .createTime(reqDto.getCreateTime())
                .expireTime(reqDto.getExpireTime())
                .anniversary(reqDto.getAnniversary())
                .build();
        log.info("세션 생성");
        return sessionRepository.save(session);
    }

    /*
     * description: 세션 타입 조회
     * return: 세션 타입 리스트 반환
     * author: 김다은
     * */
    public List<SessionType> getSessionTypeList() {
        return sessionTypeRepository.findAll();
    }

    public Session getSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
    }

    // userId를 기반으로 Session 리스트를 반환하는 메소드, 예외 처리까지
    public List<Session> getSessionByUserId(Long userId) {
        return sessionRepository.findAllByUserUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
    }


    @Transactional
    public boolean deleteSession(Long sessionId) {
        Session session = findSession(sessionId);
        try {
            sessionRepository.delete(session);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // create_time과 expire_time이 유효한지 확인
    public boolean isValidTime(LocalDateTime createTime, LocalDateTime expireTime) {
        if (createTime.isAfter(expireTime)) {
            return false;
        }
        return true;
    }

    public List<SessionMessage> getSessionMessage(Long sessionId) {
        Session session = findSession(sessionId);
        List<SessionMessage> sessionMessageList = session.getSessionMessagesList();
        return sessionMessageList;
    }

    public Session findSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
    }

    @Transactional
    public SessionMessage createSessionMessage(SessionMessageReqDto sessionMessageReqDto) {
        Session session = findSession(sessionMessageReqDto.getSessionId());
        SessionMessage sessionMessage = null;
        // img를 첨부하지 않은 경우
        if (sessionMessageReqDto.getImg() == null) {
            sessionMessageReqDto.setImg("");
        }
        // img가 gifticon이 아닌 경우 (판별은 gifticonService에서 구현)
        sessionMessage = SessionMessage.builder()
                .session(session)
                .field(sessionMessageReqDto.getField())
                .createTime(sessionMessageReqDto.getCreateTime())
                .expireTime(sessionMessageReqDto.getExpireTime())
                .nickname(sessionMessageReqDto.getNickname())
                .build();
        // gifticon인 경우 gifticonService에서 처리 (기프티콘 저장)
        // gifticonService.saveGifticon(sessionMessageReqDto.getImg(),session.getUserId()); // 기프티콘 저장(미구현) -> 기프티콘정보, 선물할 유저 아이디

        // sessionMessage 저장
        sessionMessageRepository.save(sessionMessage);

        return sessionMessage;
    }

    public SessionMessage getSessionMessageById(Long sessionMessageId) {
        Session session = findSession(sessionMessageId);
        return sessionMessageRepository.findById(sessionMessageId).orElseThrow(() -> new CustomException(ErrorCode.SESSION_MESSAGE_NOT_FOUND));
    }
}
