package com.ssafy.api.controller;

import com.ssafy.api.dto.GifticonReqDto;
import com.ssafy.api.dto.OcrReqDto;
import com.ssafy.api.service.GifticonService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Api(value = "Gifticon Api", tags = {"Gifticon"})
@RestController
@RequestMapping("/api/gifticon")
public class GifticonController {

    @Autowired
    GifticonService gifticonService;

    @PostMapping("/ocr")
    @ApiOperation(value = "기프티콘 ocr 분석", notes = "<strong>기프티콘 이미지(base64 인코딩)</strong>를 받아" +
            " <strong>정제된 기프티콘 정보</strong>를 반환한다.")

    public ResponseEntity<? extends BasicResponse> detectText(@RequestBody OcrReqDto ocrReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.detect(ocrReqDto)));
    }

    @PostMapping("/upload/{userId}/{store}/{period}")
    @ApiOperation(value = "기프티콘 저장", notes = "<strong>기프티콘 이미지와 정보</strong>를 받아" +
            " <strong>기프티콘</strong>을 db와 S3에 저장한다.")

    public ResponseEntity<? extends BasicResponse> createGifticon(@PathVariable Long userId, @PathVariable String store,
                                                                  @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate period, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.createGifticon(userId, store, period, file)));
    }

}
