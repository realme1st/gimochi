package com.ssafy.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
@Slf4j
public class AmazonS3Util {

    private final AmazonS3Client amazonS3Client;

    private final String bucket = "mygimochi";


    public String upload(MultipartFile multipartFile, String userAccessToken) throws IOException { // 객체 업로드

        String originalFileName = multipartFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // 파일 확장자(png)
        String contentType = multipartFile.getContentType(); // 파일 확장자(image/png)

        long size = multipartFile.getSize(); // 파일 크기

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(contentType);
        objectMetaData.setContentLength(size);

        // 확장자명이 존재하지 않을 경우 전체 취소 처리 x
        if (ObjectUtils.isEmpty(contentType)) {
            throw new CustomException(ErrorCode.INVALID_REQUEST); // 나중에 수정 필요
        } else if (!(contentType.equals(ContentType.IMAGE_JPEG.toString())
                || contentType.equals(ContentType.IMAGE_PNG.toString()))) {
            throw new CustomException(ErrorCode.INVALID_REQUEST); // 나중에 수정 필요
        }

        // 파일명 중복을 피하기위해 유저 토큰 + 타임스탬프 추가
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"));
        String fileName = userAccessToken + formatDate + "." + ext; // 최종 파일명

        // 이미 똑같은 파일명이 존재하는 경우
        boolean isExistObject = amazonS3Client.doesObjectExist(bucket, fileName);
        if(isExistObject) throw new CustomException(ErrorCode.INVALID_REQUEST); // 나중에 수정 필요

        // S3로 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetaData)
        );

        return fileName;
    }

    public boolean delete(String filePath) {
        boolean isExistObject = amazonS3Client.doesObjectExist(bucket, filePath);
        if(isExistObject) {
            amazonS3Client.deleteObject(bucket, filePath);
            return true;
        } else {
            return false;
        }

    }

}
