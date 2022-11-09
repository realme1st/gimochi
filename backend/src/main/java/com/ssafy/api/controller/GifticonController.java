package com.ssafy.api.controller;

import com.ssafy.api.dto.GifticonReqDto;
//import com.ssafy.api.service.GifticonService;
import com.ssafy.api.service.GifticonService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/ocr/{userId}")
    @ApiOperation(value = "기프티콘 ocr 분석", notes = "<strong>기프티콘 이미지</strong>를 받아" +
            " <strong>정제된 기프티콘 정보</strong>를 반환한다.")

    public ResponseEntity<? extends BasicResponse> detectText(@PathVariable Long userId,
                                                               @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.detect(userId, file)));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "기프티콘 저장", notes = "<strong>기프티콘 이미지와 정보</strong>를 받아" +
            "<strong>기프티콘</strong>을 db와 S3에 저장한다." + "<br>" +
            "store : 사용처, ex) 바나프레소" + "<br>" +
            "period : 유효기간, ex) 2022-12-31")

    public ResponseEntity<? extends BasicResponse> createGifticon(@RequestPart("gifticon") GifticonReqDto gifticonReqDto,
                                                                  @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.createGifticon(gifticonReqDto, file)));
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "회원이 등록한 기프티콘 조회", notes = "<strong>유저 아이디</strong>를 받아" +
            " <strong>기프티콘 정보</strong>를 반환한다.")

    public ResponseEntity<? extends BasicResponse> getGifticonByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.getGifticonByUserId(userId)));
    }

    @DeleteMapping("/{userId}/{gifticonId}")
    @ApiOperation(value = "회원이 선택한 기프티콘 삭제", notes = "<strong>유저 아이디와 기프티콘 아이디</strong>를 받아" +
            " <strong>해당 기프티콘을 삭제</strong> 한다.")

    public ResponseEntity<? extends BasicResponse> deleteGifticon(@PathVariable Long userId, @PathVariable Long gifticonId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.deleteGifticon(userId, gifticonId)));
    }

}
