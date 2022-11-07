package com.ssafy.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.api.client.util.Value;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class AmazonS3Util {

    private final AmazonS3Client amazonS3Client;

    private final String bucket = "mygimochi";


    public String upload(MultipartFile multipartFile, String userAccessToken) throws IOException { // 객체 업로드

        String originalFileName = multipartFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String contentType = multipartFile.getContentType(); // 파일의 확장자 추출

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

        String url = null;

//        File uploadFile = convert(multipleFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        // 파일명 중복을 피하기위해 날짜 추가
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"));

        // String fileName = dirName + formatDate + uploadFile.getName();
        String fileName = userAccessToken + formatDate + "." + ext; // 최종 파일명

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetaData)
        );

        // put - S3로 업로드
        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

//        // 로컬 파일 삭제
//        removeFile(uploadFile);

        url = uploadImageUrl;
        return url;
    }

    private Optional<File> convert(MultipartFile file) throws IOException { // multipartFile -> File
        File convertFile = new File(file.getOriginalFilename());
        file.transferTo(convertFile);
        return Optional.of(convertFile);
    }

    private String putS3(File uploadFile, String fileName) { // S3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.BucketOwnerRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File targetFile) { // 로컬파일 삭제
        if (targetFile.exists()) {
            if (targetFile.delete()) {
                log.info("파일이 삭제되었습니다.");
            } else {
                log.info("파일이 삭제되지 못했습니다.");
            }
        }
    }

}
