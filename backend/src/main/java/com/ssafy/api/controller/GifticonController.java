package com.ssafy.api.controller;

import com.ssafy.api.dto.OcrReqDto;
import com.ssafy.api.service.GifticonService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Gifticon Api", tags = {"Gifticon"})
@RestController
@RequestMapping("/api/gifticon")
public class GifticonController {

    @Autowired
    GifticonService gifticonService;

    @PostMapping("/ocr")
    @ApiOperation(value = "기프티콘 ocr 분석", notes = "<strong>기프티콘 이미지(base64 인코딩)</strong>를 받아" +
            " <strong>정제된 기프티콘 정보</strong>를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> detectText(@RequestBody OcrReqDto ocrReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.detect(ocrReqDto)));
    }

}
