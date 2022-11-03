package com.ssafy.api.controller;

import com.ssafy.api.dto.GifticonReqDto;
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

@Api(value = "Session API", tags = {"Session"})
@RestController
@RequestMapping("/api/gifticon")
public class GifticonController {

    @Autowired
    GifticonService gifticonService;

    /*      Session      */
    @PostMapping("/ocr")
    @ApiOperation(value = "기프티콘 생성", notes = "<strong>기프티콘 이미지(base64 인코딩)</strong>를 받아" +
            "<strong>정제된 기프티콘 정보</strong>를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> createSession(@RequestBody GifticonReqDto gifticonReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(gifticonService.);
    }


    @GetMapping("/{sessionId}")
    @ApiOperation(value = "sessionId 기반 세션 조회", notes = "<strong>세션 id를 입력하여</strong> 세션을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.getSession(sessionId)));
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "userId 기반 세션 조회", notes = "<strong>user id를 입력하여</strong> 세션을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSessionByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.getSessionByUserId(userId)));
    }


    @DeleteMapping("/{sessionId}")
    @ApiOperation(value = "세션 삭제", notes = "<strong>세션 id를 입력하여</strong> 세션을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> deleteSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.deleteSession(sessionId)));
    }



    /*      Session Message      */
    //sessionId에 해당하는 sessionMessage 생성
    @PostMapping("/message")
    @ApiOperation(value = "세션 메세지 생성", notes = "<strong>세션 메세지 정보를 입력하여</strong> 세션 메세지를 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> createSessionMessage(@RequestBody SessionMessageReqDto sessionMessageReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.createSessionMessage(sessionMessageReqDto)));
    }

    //sessionMessageId에 해당하는 sessionMessage 조회
    @GetMapping("/message/{sessionMessageId}")
    @ApiOperation(value = "sessionMessageId 기반 세션 메세지 조회", notes = "<strong>세션 메세지 id를 입력하여</strong> 세션 메세지를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSessionMessageById(@PathVariable Long sessionMessageId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.getSessionMessageById(sessionMessageId)));
    }

    //sessionId에 해당하는 sessionMessage 조회
    @GetMapping("/message/all/{sessionId}")
    @ApiOperation(value = "sessionId 기반 세션 메세지 조회", notes = "<strong>세션 id를 입력하여</strong> 세션 메세지를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSessionMessage(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.getSessionMessage(sessionId)));
    }

}
