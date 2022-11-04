package com.ssafy.api.service;

import com.ssafy.api.dto.OcrReqDto;
import com.ssafy.api.dto.OcrResDto;
import com.ssafy.common.util.VisionApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service("GiftconService")
@Transactional(readOnly = true)
@Slf4j
public class GifticonService {
    public OcrResDto detect(OcrReqDto ocrReqDto) {

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
                    .userId(ocrReqDto.getUserId())
                    .gifticonUsage("바나프레소")
                    .gifticonExp("")
                    .build();

            /*
            * 파일 삭제
            * */
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
