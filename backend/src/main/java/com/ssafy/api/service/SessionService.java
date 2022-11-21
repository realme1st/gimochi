package com.ssafy.api.service;

import com.ssafy.api.dto.FriendDto;
import com.ssafy.api.request.SessionMessageReqDto;
import com.ssafy.api.request.SessionReqDto;
import com.ssafy.api.response.*;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.SessionMessageRepository;
import com.ssafy.db.repository.SessionRepository;
import com.ssafy.db.repository.SessionTypeRepository;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("SessionService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final SessionMessageRepository sessionMessageRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final GifticonService gifticonService;

    /*
     * description: 세션 생성
     * return: 생성된 세션
     * */
    @Transactional
    public SessionResDto createSession(SessionReqDto reqDto) {
//        throw new CustomException(E)
        // 유효한 사용자인지 검증
        User user = userRepository.findByUserId(reqDto.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 유효한 세션 타입인지 검증
        SessionType sessionType = sessionTypeRepository.findSessionTypeBySessionTypeId(reqDto.getSessionTypeId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SESSION_TYPE));
        // Sesison 생성
        System.out.println("입력"+reqDto.getName());
        Session session = SessionResDto.createSessionEntity(reqDto, user);
        log.info("세션 생성");
        // Response 생성
        SessionResDto sessionResDto = null;
        try{
            Long sessionId = sessionRepository.save(session).getSessionId();
            session.setSessionId(sessionId);
            sessionResDto = SessionResDto.toDto(user, session);
        }catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.SESSION_SAVE_ERROR);
        }
        return sessionResDto;
    }


    /*
     * description: 세션 타입 조회
     * return: 세션 타입 리스트 반환
     * */
    public List<SessionType> getSessionTypeList() {
        return sessionTypeRepository.findAll();
    }

    /*
     * description: sessionId로 Session 조회
     * return: Session
     * */

    public SessionDetailResDto getSession(Long sessionId) {
        // Sesison 조회
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
        List<SessionMessage> sessionMessageList = session.getSessionMessagesList();

        return SessionDetailResDto.toDto(session, sessionMessageList);
    }

    /*
     * description : 세션 메세지 생성을 위한 메소드
     * @param : userId - 메세지를 보낸 유저의 id
     * @return : user가 작성한 세션 메세지 목록
     */

    public SessionDetailListResDto getSessionByUserId(Long userId) {
        List<Session> sessionList = sessionRepository.findAllByUserUserIdOrderByAnniversary(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
        return new SessionDetailListResDto(SessionDetailListResDto.toDtoList(sessionList));
    }

    /*
    * description : 세션 메세지 삭제를 위한 메소드
    * @param : sessionId - 메세지를 보낸 세션의 id
    * return : 성공 여부
    * */
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

    /*
     * description: sessionId에 해당하는 세션메세지 리스트 조회
     * return: 세션메세지 리스트 반환
     * */
    public List<SessionMessageResDto> getSessionMessage(Long sessionId) {
        Session session = findSession(sessionId);
        List<SessionMessage> sessionMessageList = session.getSessionMessagesList();
        return SessionMessageResDto.toDtoList(sessionMessageList);
    }

    /*
     * description: sessionMessageReqDto 로부터 얻은 정보로 세션메세지 생성
     * return: 생성된 세션메세지 반환
     * */
    @Transactional
    public boolean createSessionMessage(SessionMessageReqDto sessionMessageReqDto) {
        Session session = findSession(sessionMessageReqDto.getSessionId());
        SessionMessage sessionMessage = SessionMessageResDto.toDto(sessionMessageReqDto);
        /* sessionMessage 설정 */
        sessionMessage.setSession(session);

        // 기프티콘 아이디로 기프티콘 조회 후 세션메세지에 추가
        if(sessionMessageReqDto.getGifticonId() != null){
            Gifticon gifticon = gifticonService.getGifticonByGifticonId(sessionMessageReqDto.getGifticonId());
            sessionMessage.setGifticon(gifticon);
        }else{
            sessionMessage.setGifticon(null);
        }
        // sessionMessage 저장
        try{
            sessionMessageRepository.save(sessionMessage);
        }catch (NullPointerException e){
            throw new CustomException(ErrorCode.SESSION_MESSAGE_CREATE_SAVE_ERROR);
        }
         return true;
    }
    /*
     * description: sessionMessageId에 해당하는 세션메세지 상세 조회
     * return: 조회된 세션메세지 반환
     * */
    public SessionMessageResDto getSessionMessageById(Long sessionMessageId) {
        SessionMessage sessionMessage = sessionMessageRepository.findById(sessionMessageId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_MESSAGE_NOT_FOUND));
        return SessionMessageResDto.of(sessionMessage);
    }

    /*
     * description: sessionId에 해당하는 세션유효성 체크
     * return: 세션유효성 여부 반환
     * */
    public Session findSession(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));
    }
    /*
     * description: Time이 유효한지 확인 (createTime < expireTime)
     * return: 유효한지에 대한 boolean 반환
     * */
    public boolean isValidTime(LocalDateTime createTime, LocalDateTime expireTime) {
        if (createTime.isAfter(expireTime)) {
            return false;
        }
        return true;
    }

    /*
     * description: 만료일이 현재보다 오래됐는지 체크 (expireTime < now), 스케줄러에 적용해야함
     * return: 오래됐다면 삭제메서드 호출
     * */
    public void checkExpireTime(LocalDate expireTime) {
        List<Session> sessionList = sessionRepository.findAll();
        sessionList.forEach(session -> {
            if (session.getAnniversary().isBefore(LocalDate.now())) {
                deleteSession(session.getSessionId());
            }
        });
    }

    public List<FriendsSessionResDto> getFriendsSession(Long userId) {
        // userId로 친구목록 조회
        List<FriendDto> friendsList = userService.getFollowerList(userId);
        List<Long> userIdList = new ArrayList<>();
        List<FriendsSessionResDto> friendsSessionResDtoList = new ArrayList<>();

        // 친구목록 조회
        for (FriendDto friend : friendsList) {
            userIdList.add(friend.getUserId());
        }

        // userId 기반 세션 조회
        for(Long id : userIdList){
            Optional<List<Session>> friendsSessionList = sessionRepository.findAllByUserUserIdOrderByAnniversary(id);
            if(friendsSessionList.isPresent()){
                List<Session> sessionList = friendsSessionList.get();
                List<SessionResDto> sessionResDtoList = new ArrayList<>();
                // 해당 유저의 세션 리스트 저장
                for(Session session : sessionList){
                    SessionResDto dto = SessionResDto.builder()
                            .anniversary(session.getAnniversary())
                            .sessionTypeId(session.getSessionTypeId())
                            .sessionId(session.getSessionId())
                            .name(session.getName())
                            .userId(session.getUser().getUserId())
                            .userName(session.getUser().getUserNickname())
                            .build();
                    sessionResDtoList.add(dto);
                }
                // 친구목록에 해당 유저의 세션정보 저장
                FriendsSessionResDto friendsSessionResDto = FriendsSessionResDto.builder()
                        .userId(id)
                        .list(sessionResDtoList)
                        .build();
                friendsSessionResDtoList.add(friendsSessionResDto);
            }else{
                log.info("친구목록 없음");
            }

        }

        return friendsSessionResDtoList;
    }
}
