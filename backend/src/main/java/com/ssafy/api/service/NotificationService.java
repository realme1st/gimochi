package com.ssafy.api.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

public class NotificationService {

    @Autowired
    UserRepository userRepository;
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

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
    public void sendByTokenList(List<String> registrationTokens) {
        // 메시지 만들기
        MulticastMessage message = MulticastMessage.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle("제목").setBody("내용").build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("gimochi")//안드로이드 내 리소스 폴더 경로?
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

                System.out.println("List of tokens that caused failures: " + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            System.out.println("cannot send to memberList push message. error info : {}");
            System.out.println(e.getMessage());
        }
    }
}
