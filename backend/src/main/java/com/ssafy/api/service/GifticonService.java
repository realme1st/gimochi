package com.ssafy.api.service;

import com.ssafy.api.dto.GifticonReqDto;
import com.ssafy.api.dto.OcrReqDto;
import com.ssafy.api.dto.OcrResDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service("GiftconService")
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class GifticonService {

    private final UserRepository userRepository;

    private final AmazonS3Util amazonS3Util;

    private final GifticonRepository gifticonRepository;

    public OcrResDto detect(OcrReqDto ocrReqDto) {

        User user = userRepository.findByUserId(ocrReqDto.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String base64Enc = ocrReqDto.getGifticonEnc(); // base64 인코딩 된 이미지 파일
        String fileExtension = ocrReqDto.getGifticonExtension(); // 해당 파일의 확장자
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")); // 파일명

        String separator = File.separator;
        String filePath = null;
        String linuxDir = "/image_cache/";
        if(separator.equals("\\")) {
            filePath = FileSystemView.getFileSystemView().getHomeDirectory().getPath() + separator + fileName +
                    "." + fileExtension; // 윈도우 바탕화면 경로
        } else if(separator.equals("/")) {
            filePath = FileSystemView.getFileSystemView().getHomeDirectory().getPath() + linuxDir +
                    fileName + "." + fileExtension; // 리눅스 홈/image_cache 경로
        }

        // base64 디코딩
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] base64Dec = decoder.decode(base64Enc.getBytes());

        try {

            /*
            * 파일 임시 저장
            * */
            File file = new File(filePath);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(base64Dec);
            fileOutputStream.close();

            /*
            * 구글 vision api (ocr)
            * */
            VisionApiUtil visionApiUtil = new VisionApiUtil();
            List<String> words = visionApiUtil.detectText(filePath);
            for(String s : words) System.out.println(s);

            /*
            * 텍스트 분석 로직
            * */
            OcrResDto ocrResDto = null;
            ocrResDto = OcrResDto.builder()
                    .user(user)
                    .gifticonStore("바나프레소(아직 미완성)")
                    .gifticonPeriod("2022-12-31")
                    .build();

            /*
            * 파일 삭제
            * */
            file.delete();
            return ocrResDto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Transactional
    public Gifticon createGifticon(Long userId, String store, LocalDate period, MultipartFile multipartFile) {
        User user = userRepository.findByUserId(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String url = null;

        try {
            url = amazonS3Util.upload(multipartFile, user.getUserSocialToken());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Gifticon gifticon = Gifticon.builder()
                .user(user)
                .gifticonStore(store)
                .gifticonPeriod(period)
                .gifticonUsed(false)
                .gifticonPath(url)
                .build();

        return gifticonRepository.save(gifticon);
    }

}
