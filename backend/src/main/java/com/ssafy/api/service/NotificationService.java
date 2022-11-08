package com.ssafy.api.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.ssafy.api.dto.MultiMessageReqDto;
import com.ssafy.api.dto.SingleMessageReqDto;
import com.ssafy.db.entity.Gifticon;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

//    @Value("gimochi-6f7fe-firebase-adminsdk-wfw15-6edda71fbe.json")
//    private String FCM_PRIVATE_KEY_PATH;

    // 메시징만 권한 설정
    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    // fcm 기본 설정 진행
    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase application has been initialized");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // spring 뜰때 알림 서버가 잘 동작하지 않는 것이므로 바로 죽임
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public User saveToken(String accessToken, String firebaseToken){
        User user = userRepository.findByUserSocialToken(accessToken).get();
        user.setFirebaseToken(firebaseToken);
        userRepository.save(user);

        return user;
    }


    // 알림 보내기
    public boolean sendToUserList(MultiMessageReqDto multiMessageReqDto, List<User> userList) {

        List<String> registrationTokens = new ArrayList<>();
        User sender = userRepository.findByUserId(multiMessageReqDto.getUserId()).get();
        int type = multiMessageReqDto.getType();

        for (User user: userList) {
            registrationTokens.add(user.getUserFbToken());
        }

        String title = setMultiMessageTitle(type);
        String body = setMultiMessageBody(sender.getUserNickname(), type);

        // 메시지 만들기
        MulticastMessage message = MulticastMessage.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("gimochi")//안드로이드 내 리소스 폴더 경로!
                                .setColor("#f45342")
                                .build())
                        .build())
                .addAllTokens(registrationTokens)
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();
                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        // The order of responses corresponds to the order of the registration tokens.
                        failedTokens.add(registrationTokens.get(i));
                    }
                }
                log.info("List of tokens that caused failures:" + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            log.info("cannot send to memberList push message. error info : {}");
            log.info(e.getMessage());

            return  false;
        }
        return true;
    }
    @Transactional
    public boolean sendToUser(SingleMessageReqDto singleMessageReqDto) {
        User sender = userRepository.findByUserId(singleMessageReqDto.getSenderId()).get();
        User receiver = userRepository.findByUserId(singleMessageReqDto.getReceiverId()).get();
        int type = singleMessageReqDto.getType();
        String registrationToken = receiver.getUserFbToken();

        String title = setSingleMessageTitle(type);
        String body = setSingleMessageBody(sender.getUserNickname(), type);

        Message message = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("gimochi")//안드로이드 내 리소스 폴더 경로?
                                .setColor("#f45342")
                                .build())
                        .build())
                .setToken(registrationToken)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        }catch (FirebaseMessagingException e){
            System.out.println("cannot send to memberList push message. error info : {}");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    public boolean sendDailyNotification(Long userId, int day, boolean isOne, List<Gifticon> gifticons) {
        User receiver = userRepository.findByUserId(userId).get();
        String registrationToken = receiver.getUserFbToken();

        String title = "곧 사라지는 기프티콘이 있어요!";
        String body = "";

        if(isOne){
            body = gifticons.get(0).getGifticonStore() + " 기프티콘이 "+day+"일 뒤에 사라져요!";
        }else{
            body = gifticons.size() + "개의 기프티콘이 "+day+" 일 뒤에 사라져요!";
        }


        Message message = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("gimochi")//안드로이드 내 리소스 폴더 경로?
                                .setColor("#f45342")
                                .build())
                        .build())
                .setToken(registrationToken)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        }catch (FirebaseMessagingException e){
            System.out.println("cannot send to memberList push message. error info : {}");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String setMultiMessageTitle(int type){
        if(type == 1){
            return "친구가 기념일을 생성했어요!";
        }else if(type ==2){
            return "";
        }else{
            return "";
        }
    }

    public String setMultiMessageBody(String name, int type){
        if(type == 1){
            return name+"님에게 추카포카 메세지를 남겨주세요.";
        }else if(type ==2){
            return "x일 뒤에 사라지는 기프티콘이 x개 있어요!";
        }else{
            return "";
        }
    }

    public String setSingleMessageTitle(int type){
        if(type == 1){
            return "친구가 챌린지에 초대했어요!";
        }else{
            return "";
        }
    }

    public String setSingleMessageBody(String name, int type){
        if(type == 1){
            return name+"님이 챌린지에 초대하셨습니다. 수락을 눌러 참여하세요.";
        }else{
            return "";
        }
    }
}