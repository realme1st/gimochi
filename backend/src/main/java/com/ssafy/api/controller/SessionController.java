package com.ssafy.api.controller;

import com.ssafy.api.dto.SessionMessageReqDto;
import com.ssafy.api.dto.SessionReqDto;
import com.ssafy.api.service.SessionService;
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
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    SessionService sessionService;

    @GetMapping("/type")
    @ApiOperation(value = "세션 타입  조회", notes = "<strong>세션 타입을 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSessionTypeList() {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.getSessionTypeList()));
    }

    @PostMapping()
    @ApiOperation(value = "세션 생성", notes = "<strong>세션 정보를 입력하여</strong> 세션을 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> createSession(@RequestBody SessionReqDto sessionReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.createSession(sessionReqDto)));
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

    //sessionId에 해당하는 sessionMessage 조회
    @GetMapping("/message/{sessionId}")
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

    //sessionId에 해당하는 sessionMessage 생성
    @PostMapping("/message/{sessionId}")
    @ApiOperation(value = "세션 메세지 생성", notes = "<strong>세션 메세지 정보를 입력하여</strong> 세션 메세지를 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당하는 정보 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> createSessionMessage(@PathVariable Long sessionId, @RequestBody SessionMessageReqDto sessionMessageReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(sessionService.createSessionMessage(sessionId, sessionMessageReqDto)));
    }
}