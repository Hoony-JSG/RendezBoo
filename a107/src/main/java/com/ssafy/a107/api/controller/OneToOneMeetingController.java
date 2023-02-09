package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.OneToOneChatReq;
import com.ssafy.a107.api.request.OneToOneFinalChoiceReq;
import com.ssafy.a107.api.request.OneToOneMeetingJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.OneToOneMeetingChatRes;
import com.ssafy.a107.api.response.OneToOneMeetingRoomRes;
import com.ssafy.a107.api.service.OneToOneMeetingService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "일대일 미팅방 관련 컨트롤러", tags = {"OneToOneMeeting"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/onetoone")
public class OneToOneMeetingController {

    private final OneToOneMeetingService oneToOneMeetingService;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 1. Session Initialization, 2. Connection Creation
     *
     * @return MeetingRoomRes dto
     */
    @ApiOperation("유저 시퀀스 기반으로 일대일 매칭을 신청하면 토큰을 전달")
    @PostMapping("")
    public ResponseEntity<MeetingRoomRes> joinMatch(@RequestBody OneToOneMeetingJoinReq oneToOneMeetingJoinReq) throws OpenViduJavaClientException, OpenViduHttpException, NotFoundException {
        MeetingRoomRes meetingRoomRes = oneToOneMeetingService.joinMatch(oneToOneMeetingJoinReq);
        return ResponseEntity.status(HttpStatus.OK).body(meetingRoomRes);
    }


    @ApiOperation("관리자 전용 -- 전체 일대일 미팅방 정보")
    @GetMapping("/{status}")
    public ResponseEntity<List<OneToOneMeetingRoomRes>> getOneToOneMeetingRooms(@PathVariable Byte status) {
        List<OneToOneMeetingRoomRes> list = oneToOneMeetingService.getOneToOneMeetingRooms(status);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @ApiOperation("일대일 매칭의 종료")
    @DeleteMapping("/{meetingRoomSeq}")
    public ResponseEntity closeMatch(@PathVariable Long meetingRoomSeq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.closeMatch(meetingRoomSeq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + meetingRoomSeq, oneToOneMeetingChatRes);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation("일대일 매칭 시작 -- ws 사용")
    @MessageMapping("/one/{meetingRoomSeq}/start")
    public void startOneToOneMatch(@PathVariable Long meetingRoomSeq) {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.startOneToOneMeeting(meetingRoomSeq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + meetingRoomSeq, oneToOneMeetingChatRes);
    }

    @ApiOperation("일대일 매칭 페이즈 2 시작 - 선글라스 벗김 -- ws 사용")
    @MessageMapping("/one/{meetingRoomSeq}/phase2")
    public void deleteGlasses(@PathVariable Long meetingRoomSeq) {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.deleteGlasses(meetingRoomSeq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + meetingRoomSeq, oneToOneMeetingChatRes);
    }

    @ApiOperation("일대일 매칭 페이즈 3 시작 - 마스크 벗김 -- ws 사용")
    @MessageMapping("/one/{meetingRoomSeq}/phase3")
    public void deleteMasks(@PathVariable Long meetingRoomSeq) {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.deleteMasks(meetingRoomSeq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + meetingRoomSeq, oneToOneMeetingChatRes);
    }

    @ApiOperation("일대일 매칭 페이즈 3 시작 - 마스크 벗김 -- ws 사용")
    @MessageMapping("/one/{meetingRoomSeq}/final")
    public void startFinalChoice(@PathVariable Long meetingRoomSeq) {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.finalChoiceStart(meetingRoomSeq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + meetingRoomSeq, oneToOneMeetingChatRes);
    }

    @ApiOperation("일대일 매칭 페이즈 3 시작 - 마스크 벗김 -- ws 사용")
    @MessageMapping("/one/choice")
    public void finalChoice(@RequestBody OneToOneFinalChoiceReq oneToOneFinalChoiceReq) throws NotFoundException {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.finalChoice(oneToOneFinalChoiceReq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + oneToOneFinalChoiceReq.getMeetingRoomSeq(), oneToOneMeetingChatRes);
    }

    @ApiOperation("일대일 매칭 페이즈 3 시작 - 마스크 벗김 -- ws 사용")
    @MessageMapping("/one/chat")
    public void chatting(@RequestBody OneToOneChatReq oneToOneChatReq) {
        OneToOneMeetingChatRes oneToOneMeetingChatRes = oneToOneMeetingService.chatting(oneToOneChatReq);
        simpMessageSendingOperations.convertAndSend("/sub/one/" + oneToOneChatReq.getMeetingRoomSeq(), oneToOneMeetingChatRes);
    }

}
