package com.ssafy.api.controller;

import com.ssafy.api.dto.GifticonInfoReqDto;
import com.ssafy.api.dto.GifticonPresentReq;
import com.ssafy.api.dto.GifticonStorePeriodReq;
import com.ssafy.api.dto.GifticonUsedReq;
import com.ssafy.api.service.GifticonService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Gifticon Api", tags = {"Gifticon"})
@RestController
@RequestMapping("/api/gifticon")
public class GifticonController {

    @Autowired
    GifticonService gifticonService;

    @PostMapping("/ocr/{userId}")
    @ApiOperation(value = "기프티콘 ocr 분석", notes = "<strong>기프티콘 이미지</strong> 를 받아" +
            " <strong>정제된 기프티콘 정보</strong> 를 반환한다. (정보가 없다면 \"\" 값 반환)")

    public ResponseEntity<? extends BasicResponse> detectText(@PathVariable Long userId,
                                                               @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.detect(userId, file)));
    }

    @PostMapping("/info")
    @ApiOperation(value = "기프티콘 정보 저장 (정보 먼저 보내고, 이미지 보내주세요)", notes = "<strong>기프티콘 정보</strong> 를 받아" +
            " <strong>기프티콘 정보</strong> 를 db에 저장한다." + "<br>" +
            "store : 사용처, ex) 바나프레소" + "<br>" +
            "period : 유효기간, ex) 2022-12-31")

    public ResponseEntity<? extends BasicResponse> createGifticonInfo(@RequestBody GifticonInfoReqDto gifticonInfoReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.createGifticonInfo(gifticonInfoReqDto)));
    }

    @PostMapping("/img/{gifticonId}")
    @ApiOperation(value = "기프티콘 이미지 저장 (정보 먼저 보내고, 이미지 보내주세요)", notes = "<strong>기프티콘 이미지 정보</strong> 를 받아" +
            " <strong>기프티콘 이미지 정보</strong> 를 db와 S3에 저장한다.")

    public ResponseEntity<? extends BasicResponse> createGifticonImg(@PathVariable Long gifticonId,
                                                                     @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.createGifticonImg(gifticonId, file)));
    }

    @GetMapping("/uid/{userId}")
    @ApiOperation(value = "회원이 등록한 기프티콘 조회", notes = "<strong>유저 아이디</strong> 를 받아" +
            " <strong>기프티콘 정보</strong> 를 반환한다.")

    public ResponseEntity<? extends BasicResponse> getGifticonByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.getGifticonByUserId(userId)));
    }

    @GetMapping("/gid/{gifticonId}")
    @ApiOperation(value = "기프티콘 식별자로 기프티콘 조회", notes = "<strong>기프티콘 식별자</strong> 를 받아" +
            " <strong>기프티콘 정보</strong> 를 반환한다.")

    public ResponseEntity<? extends BasicResponse> getGifticonByGifticonId(@PathVariable Long gifticonId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.getGifticonByGifticonId(gifticonId)));
    }

    @DeleteMapping("/{userId}/{gifticonId}")
    @ApiOperation(value = "회원이 선택한 기프티콘 삭제", notes = "<strong>유저 아이디와 기프티콘 아이디</strong> 를 받아" +
            " <strong>해당 기프티콘을 삭제</strong> 한다.")

    public ResponseEntity<? extends BasicResponse> deleteGifticon(@PathVariable Long userId, @PathVariable Long gifticonId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.deleteGifticon(userId, gifticonId)));
    }

    @PutMapping
    @ApiOperation(value = "기프티콘 선물", notes = "<strong>선물하는 유저 아이디와 선물받을 유저 아이디, 기프티콘 아이디</strong> 를 받아" +
            " <strong>해당 기프티콘을 선물</strong> 한다.")

    public ResponseEntity<? extends BasicResponse> presentGifticon(@RequestBody GifticonPresentReq gifticonPresentReq) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.updateGifticonUser(gifticonPresentReq)));
    }

    @PutMapping("/used")
    @ApiOperation(value = "기프티콘 상태 수정", notes = "<strong>유저 아이디와 기프티콘 아이디</strong> 를 받아" +
            " <strong>해당 기프티콘의 사용여부를 변경</strong> 한다.")

    public ResponseEntity<? extends BasicResponse> updateGifticonUsed(@RequestBody GifticonUsedReq gifticonUsedReq) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.updateGifticonUsed(gifticonUsedReq)));
    }

    @PutMapping("/store/period")
    @ApiOperation(value = "기프티콘 정보 수정", notes = "<strong>유저 아이디와 기프티콘 아이디</strong> 를 받아" +
            " <strong>해당 기프티콘의 사용 기한과 사용처를 변경</strong> 한다.")

    public ResponseEntity<? extends BasicResponse> updateGifticonStorePeriod(@RequestBody GifticonStorePeriodReq gifticonStorePeriodReq) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.updateGifticonStorePeriod(gifticonStorePeriodReq)));
    }

    /*
     * 파일과 reqDto 동시에 받아서 처리하는 컨트롤러 (나중에 다시 해보자)
    @PostMapping("/upload")
    @ApiOperation(value = "기프티콘 저장", notes = "<strong>기프티콘 이미지와 정보</strong>를 받아" +
            "<strong>기프티콘</strong>을 db와 S3에 저장한다." + "<br>" +
            "store : 사용처, ex) 바나프레소" + "<br>" +
            "period : 유효기간, ex) 2022-12-31")

    public ResponseEntity<? extends BasicResponse> createGifticon(@RequestPart("gifticon") GifticonReqDto gifticonReqDto,
                                                                  @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.createGifticon(gifticonReqDto, file)));
    }
*/

}
