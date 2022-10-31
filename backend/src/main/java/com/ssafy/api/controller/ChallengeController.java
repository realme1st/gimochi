package com.ssafy.api.controller;


import com.ssafy.api.dto.ChallengeReqDto;
import com.ssafy.api.service.ChallengeService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import com.ssafy.db.entity.Challenge;
import io.swagger.annotations.*;
import com.ssafy.api.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@Api(value = "Challenge API", tags = {"Challenges"})
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    @Autowired
    ChallengeService challengeService;

    @PostMapping()
    @ApiOperation(value = "챌린지 생성", notes = "<strong>챌린지 정보를 입력하여</strong> 챌린지를 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })

    public ResponseEntity<? extends BasicResponse> createChallenge
            (@RequestBody @ApiParam(value = "챌린지") ChallengeReqDto challengeReqDto) {

        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.createChllenge(challengeReqDto)));
    }

    @GetMapping()
    @ApiOperation(value = "모든 챌린지 조회", notes = "<strong>챌린지 전체정보 조회</strong> 존재하는 모든 챌린지를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getSessionTypeList(){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.getChallengeList()));
    }

    @GetMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 조회(challengeId기반)", notes = "<strong>챌린지 ID를 입력하여</strong> 챌린지를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getChallengeListByChallengeId(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.getChallengeListByUserId(challengeId)));
    }

    @DeleteMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 삭제(challengeId기반)", notes = "<strong>챌린지 ID를 입력하여</strong> 챌린지를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> deleteChallenge(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.deleteChallenge(challengeId)));
    }

    @GetMapping("/user/{challengeId}")
    @ApiOperation(value = "하나의 챌린지에 속한 유저들 조회", notes = "<strong>챌린지 ID를 입력하여</strong> 챌린지에 속한 유저리스트를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 404, message = "해당하는 챌린지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> getChallengeUserList(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.getChallengeUserList(challengeId)));
    }
}
