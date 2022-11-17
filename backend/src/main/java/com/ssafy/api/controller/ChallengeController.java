package com.ssafy.api.controller;


import com.ssafy.api.dto.UpdateChallengeAuthReqDto;
import com.ssafy.api.dto.VoteReqDto;
import com.ssafy.api.request.ChallengeAuthReqDto;
import com.ssafy.api.request.ChallengeInviteReqDto;
import com.ssafy.api.request.ChallengeReqDto;
import com.ssafy.api.response.*;
import com.ssafy.api.service.ChallengeService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import com.ssafy.db.entity.Challenge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "Challenge API", tags = {"Challenges"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {


    private final ChallengeService challengeService;

    @PostMapping()
    @ApiOperation(value = "챌린지 생성", notes = "<strong>챌린지 정보를 입력하여</strong> 챌린지와 챌린지유저 정보를 만든다.")
    public ResponseEntity<CommonResponseEntity<ChallengeResDto>> createChallenge
            (@RequestBody @ApiParam(value = "챌린지") ChallengeReqDto challengeReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.createChllenge(challengeReqDto)));
    }


    @GetMapping()
    @ApiOperation(value = "모든 챌린지 조회", notes = "<strong>챌린지 전체정보 조회</strong> 존재하는 모든 챌린지를 조회한다.")
    public ResponseEntity<? extends BasicResponse> getSessionTypeList(){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.getChallengeList()));
    }



    @GetMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 조회(challengeId기반)", notes = "<strong>챌린지 ID를 입력하여</strong> 해당챌린지를 상세 조회한다.")
    public ResponseEntity<CommonResponseEntity<ChallengeDetailResDto>> getChallengeByChallengeId(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.getChallenge(challengeId)));
    }

    @PutMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 수정", notes = "<strong>챌린지 생성 시간과 종료시간에 따라 </strong> 챌린지의 진행여부를 변경한다.")
    public ResponseEntity<? extends BasicResponse> updateChallenge(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.updateChallenge(challengeId)));
    }


    @DeleteMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 삭제(challengeId기반)", notes = "<strong>챌린지 ID를 입력하여</strong> 챌린지를 삭제한다.")

    public ResponseEntity<? extends BasicResponse> deleteChallenge(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.deleteChallenge(challengeId)));
    }


    //challengeId에 속한 UserList 조회
    @GetMapping("/userList/{challengeId}")
    @ApiOperation(value = "챌린지에 참여하는 유저리스트 조회(challengeId기반)", notes = "<strong>challengeId를 입력하여</strong> 해당 챌린지에 참여하는 유저리스트를 조회한다.")
    public ResponseEntity<CommonResponseEntity<List<UserListResDto>>> findUserListByChallengeId(@PathVariable Long challengeId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.findUserListByChallengeId(challengeId)));
    }

    @GetMapping("/challengeList/{userId}")
    @ApiOperation(value = "유저가 참여하는 챌린지리스트 조회(userId기반)", notes = "<strong>userId를 입력하여</strong> 해당 유저가 참여하는 챌린지리스트를 조회한다.")
    public ResponseEntity<CommonResponseEntity<List<ChallengeListResDto>>> findChallengeListByUserId(@PathVariable Long userId){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.findChallengeListByUserId(userId)));
    }

    @PostMapping("/challengeInvite")
    @ApiOperation(value = "user_id에 해당하는 사용자에게 Challenge 초대장 전송")
    public ResponseEntity<? extends BasicResponse> createChallengeInvite(@RequestBody ChallengeInviteReqDto challengeInviteReqDto){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.createChallengeInvite(challengeInviteReqDto)));
    }

    @GetMapping("/challengeInvite/challengeList/{userId}")
    @ApiOperation(value = "user_id가 자신이 받은 Challenge 초대장들(challegeList)을 조회", notes = "<strong>userId를 입력하여</strong> 해당 유저가 초대받은 챌린지의 정보를 조회한다.")
    public ResponseEntity<CommonResponseEntity<List<ChallengeListInviteResDto>>> getChallengeInviteLIst(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.findChallengeInviteList(userId)));
    }

    @GetMapping("/challengeInvite/userList/{challengeId}")
    @ApiOperation(value = "challenge_id에 해당하는 초대장을 보낸 userList 조회", notes = "<strong>challengeId를 입력하여</strong> 초대장을 보낸 유저들을 조회한다.")
    public ResponseEntity<CommonResponseEntity<List<UserListInviteResDto>>> getChallengeInviteUserList(@PathVariable Long challengeId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.findChallengeInviteListByChallengeId(challengeId)));
    }

    // 챌린지 수락을 눌렀을 떄 , 챌린지 유저 정보 테이블에 값 추가
    @PostMapping("/challengeInvite/accept/{challengeInviteId}")
    @ApiOperation(value = "챌린지 초대장 수락", notes = "<strong>챌린지 초대장 ID를 입력하여</strong> 해당 챌린지 초대장을 수락한다.")
    public ResponseEntity<? extends BasicResponse> acceptChallengeInvite(@PathVariable Long challengeInviteId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.acceptChallengeInvite(challengeInviteId)));
    }

    @DeleteMapping("/challengeInvite/{challengeInviteId}")
    @ApiOperation(value = "챌린지 초대장 삭제", notes = "<strong>챌린지 초대장 ID를 입력하여</strong> 해당 챌린지 초대장을 거절한다.")
    public ResponseEntity<? extends BasicResponse> rejectChallengeInvite(@PathVariable Long challengeInviteId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.deleteChallengeInvite(challengeInviteId)));
    }



    @PostMapping("/challengeAuth")
    @ApiOperation(value = "챌린지 인증", notes = "<strong>챌린지 인증 정보를 입력하여</strong> 해당 챌린지 인증을 생성한다.")
    public ResponseEntity<? extends BasicResponse> createChallengeAuth(@RequestBody ChallengeAuthReqDto challengeAuthReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.createChallengeAuth(challengeAuthReqDto)));
    }


    @PutMapping("/vote")
    @ApiOperation(value = "챌린지 인증 업데이트", notes = "<strong>챌린지 인증 투표 정보를 입력하여</strong> 해당 챌린지 인증 투표에 대한 사용자의 챌린지 관련 정보를 업데이트한다.")
    public ResponseEntity<? extends BasicResponse> updateVote(@RequestBody UpdateChallengeAuthReqDto updateChallengeAuthReqDto){
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.updateChallengeAuth(updateChallengeAuthReqDto)));
    }

    @GetMapping("/challengeInfo/rank/{challengeId}/{userId}")
    @ApiOperation(value = "챌린지에서 순위 조회", notes = "<strong>챌린지 ID와 유저 ID를 입력하여</strong> 해당 챌린지에 대한 자신의 순위와 1위 유저를 조회한다.")
    public ResponseEntity<CommonResponseEntity<ChallengeInfoRankResDto>> getChallengeInfo(@PathVariable Long userId, @PathVariable Long challengeId) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(challengeService.findChallengeInfoRankByChallengeIdAndUserId(challengeId, userId)));
    }

}
