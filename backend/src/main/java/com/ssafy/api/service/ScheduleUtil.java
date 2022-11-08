package com.ssafy.api.service;

import com.ssafy.db.entity.Gifticon;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleUtil {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    //private final GifticonRepository gifticonRepository;
    //매일 아침 9시에 남은 기프티콘 유효기간 확인하고 알림 전송
    @Scheduled(cron = "0 0 9 * * *")
    public void checkGifticonPeriod() {

        //모든 유저 리스트가져오기
        List<User> userList = userRepository.findAll();

        userList.stream().map(user -> {
            List<Gifticon> gifticons = usersGifticonList(user);
            check7Days(gifticons,user);
            check3Days(gifticons,user);
            check1Day(gifticons,user);
            return usersGifticonList(user);
        });
        //유저 기프티콘 리스트 가져오기

        //남은 기간이 7일
        //남은 기간이 3일
        //남은 기간이 1일

        //
        log.info("스케줄러 실행!");
    }

    public List<Gifticon> usersGifticonList(User user) {
        return null;
    }

    public void check7Days(List<Gifticon> gifticons, User user) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,+7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daysAfter7 = formatter.format(calendar.getTime());


        //사용되지 않은 기프티콘들 확인 몇장인지 확인
        List<Gifticon> notUsedGifticons = gifticons.stream()
                .filter(gifticon -> !gifticon.isGifticonUsed())
                .filter(gifticon -> daysAfter7.equals(formatter.format(gifticon.getGifticonPeriod())))
                .collect(Collectors.toList());

        Long userId = user.getUserId();
        if(notUsedGifticons.size() == 1){
            //1장이면 정보 담아서 알림

//            notificationService.sendDailyNotification(userId, 1);

        }else if(notUsedGifticons.size() > 1){
            //여러장이면 묶어서 알림

        }

    }

    public void check3Days(List<Gifticon> gifticons, User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,+3);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daysAfter3 = formatter.format(calendar.getTime());
        //사용되지 않은 기프티콘들 확인 몇장인지 확인
        List<Gifticon> notUsedGifticons = gifticons.stream()
                .filter(gifticon -> !gifticon.isGifticonUsed())
                .filter(gifticon -> daysAfter3.equals(formatter.format(gifticon.getGifticonPeriod())))
                .collect(Collectors.toList());

        if(notUsedGifticons.size() == 1){
            //1장이면 정보 담아서 알림


        }else if(notUsedGifticons.size() > 1){
            //여러장이면 묶어서 알림

        }
    }

    public void check1Day(List<Gifticon> gifticons, User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,+1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dayAfter1 = formatter.format(calendar.getTime());
        //사용되지 않은 기프티콘들 확인 몇장인지 확인
        List<Gifticon> notUsedGifticons = gifticons.stream()
                .filter(gifticon -> !gifticon.isGifticonUsed())
                .filter(gifticon -> dayAfter1.equals(formatter.format(gifticon.getGifticonPeriod())))
                .collect(Collectors.toList());

        if(notUsedGifticons.size() == 1){
            //1장이면 정보 담아서 알림


        }else if(notUsedGifticons.size() > 1){
            //여러장이면 묶어서 알림

        }
    }
}
