package com.ssafy.api.service;

import com.google.protobuf.ByteString;
import com.ssafy.api.dto.*;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.common.util.AmazonS3Util;
import com.ssafy.common.util.VisionApiUtil;
import com.ssafy.db.entity.Gifticon;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.GifticonRepository;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("GiftconService")
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class GifticonService {

    private final UserRepository userRepository;

    private final AmazonS3Util amazonS3Util;

    private final GifticonRepository gifticonRepository;

    private final ResourceLoader resourceLoader;

/*  시작 시에 환경변수 강제 주입하는 코드 (쓰면 x)
    @PostConstruct
    public void init() throws Exception {
        System.out.println("11111=============================11111");
        File dir = new File(System.getProperty("user.dir"));
        String[] strs = dir.list();
        for(String s : strs) System.out.println(s);
        System.out.println("=============================");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("=============================");
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        System.out.println("=============================");
        Map<String, String> env = new HashMap<>();
        env.put("GOOGLE_APPLICATION_CREDENTIALS", System.getProperty("user.dir")+"/gimochi-cd8bdea6fd58.json");
        setEnv(env);
    }

    protected static void setEnv(Map<String, String> newenv) throws Exception {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        } catch (NoSuchFieldException e) {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for(Class cl : classes) {
                if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                    Field field = cl.getDeclaredField("m");
                    field.setAccessible(true);
                    Object obj = field.get(env);
                    Map<String, String> map = (Map<String, String>) obj;
                    map.clear();
                    map.putAll(newenv);
                }
            }
        }
    }
*/

    public OcrResDto detect(Long userId, MultipartFile multipartFile) {
        User user = userRepository.findByUserId(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        log.info("=============================");
        log.info(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
        log.info("=============================");

        try {
            VisionApiUtil visionApiUtil = new VisionApiUtil();
            ByteString imgBytes = ByteString.readFrom(multipartFile.getInputStream());
            List<String> words = visionApiUtil.detectText(imgBytes);
            for(String s : words) log.info(s);

            /*
            * 분석 로직 들어가야함
            * 결과가 정규식에 부합하는 경우 vs 부합하지 않는 경우
           * */

            String target = words.get(0);
            target = target.replaceAll("(\r\n|\r|\n|\n\r)", " ")
                    .replaceAll(" ", ""); // 줄바꿈, 공백 모두 제거

            List<String> stores = null;
            String filePath = "store" + File.separator + "stores.txt";

            if(File.separator.equals("\\")) { // 윈도우 로컬의 경우

                stores = FileUtils.readLines(new File(resourceLoader
                        .getResource("classpath:" + filePath).getURI()), Charset.defaultCharset());

            } else if(File.separator.equals("/")) { // ec2 배포 환경일 경우

                InputStream inputStream = new ClassPathResource("store/stores.txt").getInputStream();
                File file = File.createTempFile("stores",".txt");
                try {
                    FileUtils.copyInputStreamToFile(inputStream, file);
                    stores = FileUtils.readLines(file, Charset.defaultCharset());
                } finally {
                    IOUtils.closeQuietly(inputStream);
                }

            }

            String store = "";
            for(String s : stores) {
                if(target.contains(s)) {
                    store = s;
                    break;
                }
            }

            Pattern periodPattern = Pattern.compile("((20)([0-9]{2})|([0-9]{2}))(([^ㄱ-ㅎㅏ-ㅣa-zA-Z0-9])([0-9]{2}|[0-9]{1})){2}");
            Matcher matcher = periodPattern.matcher(target);

            String period = "";
            if(matcher.find()) {
                String temp = matcher.group();
                period = temp.replaceAll("([년]|[월]|[.]|[\\/])", "-");
            }

            Pattern barcodePattern = Pattern.compile("[0-9]{16}|([0-9]{4})(([^ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]([0-9]){4}){3})|" +
                    "[0-9]{14}|([0-9]{4})(([^ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]([0-9]){4}){2}([^ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]([0-9]){2}))|" +
                    "[0-9]{12}|([0-9]{4})(([^ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]([0-9]){4}){2})");
            matcher = barcodePattern.matcher(target);

            String code = "";
            if(matcher.find()) {
                String temp = matcher.group();
                code = temp.replaceAll("-", "");
            }

            OcrResDto ocrResDto = OcrResDto.builder()
                    .user(user)
                    .gifticonStore(store)
                    .gifticonPeriod(period)
                    .gifticonCode(code)
                    .build();

            return ocrResDto;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;

    }

    @Transactional
    public Gifticon createGifticonInfo(GifticonInfoReqDto gifticonInfoReqDto) {

        User user = userRepository.findByUserId(gifticonInfoReqDto.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        Gifticon gifticon = Gifticon.builder()
                .user(user)
                .gifticonStore(gifticonInfoReqDto.getGifticonStore())
                .gifticonPeriod(LocalDate.parse(gifticonInfoReqDto.getGifticonPeriod()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .gifticonUsed(false)
                .gifticonPath("notAllocated")
                .gifticonCode(gifticonInfoReqDto.getGifticonCode())
                .build();

        return gifticonRepository.save(gifticon);
    }

    @Transactional
    public Gifticon createGifticonImg(Long gifticonId, MultipartFile multipartFile) {

        // 기프티콘 존재하는지 확인
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID));

        // 유저 존재하는지 확인
        User user = userRepository.findByUserId(gifticon.getUser().getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        String url = null;

        try {
            url = amazonS3Util.upload(multipartFile, user.getUserSocialToken());
        } catch (IOException e) {

            log.error(e.getMessage());
        }

        String prefix = "https://mygimochi.s3.ap-northeast-2.amazonaws.com/";
        String finalUrl = prefix + url;

        gifticon.changeGifticonPath(finalUrl);

        return gifticonRepository.save(gifticon);
    }

    public List<Gifticon> getGifticonByUserId(Long userId) {
        return gifticonRepository.findAllByUserUserIdOrderByGifticonPeriod(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));
    }

    public Gifticon getGifticonByGifticonId(Long gifticonId) {
        return gifticonRepository.findByGifticonId(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID));
    }

    @Transactional
    public boolean deleteGifticonS3(Long userId, Long gifticonId) {
        // 유저 존재하는지 확인
        User user = userRepository.findByUserId(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        // 기프티콘 존재하는지 확인
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID));

        // 넘어온 유저 정보와 기프티콘의 유저정보가 같은지 비교
        if(gifticon.getUser().getUserId() != userId) {
            throw new CustomException(ErrorCode.GIFTICON_USER_NOT_FOUND);
        }

        String path = gifticon.getGifticonPath().replace("https://mygimochi.s3.ap-northeast-2.amazonaws.com/", "");
        try {
            boolean isDeleteObject = amazonS3Util.delete(path); // 아마존 S3 객체 삭제
            if(isDeleteObject) {
                gifticonRepository.delete(gifticon); // db 삭제
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            deleteGifticon(userId, gifticonId);
            return false;
        }
    }

    @Transactional
    public boolean deleteGifticon(Long userId, Long gifticonId) {
        // 유저 존재하는지 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        // 기프티콘 존재하는지 확인
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID));

        // 넘어온 유저 정보와 기프티콘의 유저정보가 같은지 비교
        if(gifticon.getUser().getUserId() != userId) {
            throw new CustomException(ErrorCode.GIFTICON_USER_NOT_FOUND);
        }

        try {
            gifticonRepository.delete(gifticon); // db 삭제
            return true;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Transactional
    public Gifticon updateGifticonUser(Long gifticonId, Long senderId, Long receiverId) {

        User userSender = userRepository.findByUserId(senderId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        User userReceiver = userRepository.findByUserId(receiverId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        // 기프티콘 존재하는지 확인
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID));

        // 넘어온 선물 보내는 유저 정보와 기프티콘의 유저정보가 같은지 비교
        if(gifticon.getUser().getUserId() != senderId) {
            throw new CustomException(ErrorCode.GIFTICON_USER_NOT_FOUND);
        }

        gifticon.changeGifticonUser(userReceiver); // 기프티콘 소유자를 받는 사람으로 변경

        return gifticon;

    }

    @Transactional
    public Gifticon updateGifticonUsed(Long userId, Long gifticonId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        // 기프티콘 존재하는지 확인
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GIFTICON_ID)); // 수정 필요

        // 넘어온 유저 정보와 기프티콘의 유저정보가 같은지 비교
        if(gifticon.getUser().getUserId() != userId) {
            throw new CustomException(ErrorCode.GIFTICON_USER_NOT_FOUND); // 수정 필요
        }

        gifticon.changeGifticonUsed(); // 기프티콘 사용여부 상태를 변경

        return gifticon;

    }

/*
 * 파일과 reqDto 동시에 받아서 처리하는 서비스 로직 (나중에 다시 해보자)
    @Transactional
    public Gifticon createGifticon(GifticonReqDto gifticonReqDto, MultipartFile multipartFile) {

        User user = userRepository.findByUserId(gifticonReqDto.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String url = null;

        try {
            url = amazonS3Util.upload(multipartFile, user.getUserSocialToken());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Gifticon gifticon = Gifticon.builder()
                .user(user)
                .gifticonStore(gifticonReqDto.getGifticonStore())
                .gifticonPeriod(LocalDate.parse(gifticonReqDto.getGifticonPeriod()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .gifticonUsed(false)
                .gifticonPath(url)
                .build();

        return gifticonRepository.save(gifticon);
    }
*/

}
