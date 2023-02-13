package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.MultiMeetingRoomCreationReq;
import com.ssafy.a107.api.request.MultiMeetingRoomJoinReq;
import com.ssafy.a107.api.request.MultiWebSocketReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.api.service.MultiMeetingRoomService;
import com.ssafy.a107.common.exception.MeetingRoomAlreadyFullException;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "단체 미팅방 APi", tags = {"MultiMeetingRoom"})
@RestController
@RequestMapping("/api/multi-meetings")
@RequiredArgsConstructor
public class MultiMeetingRoomController {
    private final MultiMeetingRoomService multiMeetingRoomService;

    /*
     *   1. Session Initialization, 2. Connection Creation
     * @return   MeetingRoomRes dto
     * */
    @ApiOperation(value = "새 단체 미팅방 생성 + openvidu 세션 생성",notes = "처음에 미팅방 만들고 세션 생성해두기(연결은 x)")
    @PostMapping("/")
    public ResponseEntity<Long> initializeMultiRoom(@RequestBody MultiMeetingRoomCreationReq multiMeetingRoomCreationReq)
            throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        Long meetingRoomSeq = multiMeetingRoomService.initializeSession(multiMeetingRoomCreationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingRoomSeq);
    }

    @ApiOperation(value = "openvidu 세션과 연결 수립하기", notes = "6명이 됐을 때 openvidu 세션 연결하는 요청")
    @PostMapping("/join")
    public ResponseEntity<MeetingRoomRes> joinOpenviduSession(@RequestBody MultiMeetingRoomJoinReq multiMeetingRoomJoinReq)
            throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException{
        MeetingRoomRes meetingRoomRes = multiMeetingRoomService.createConnection(multiMeetingRoomJoinReq);
        return ResponseEntity.status(HttpStatus.OK).body(meetingRoomRes);
    }

    @ApiOperation(value = "단체 미팅방 삭제하기", notes = "단체 미팅방 삭제하기")
    @DeleteMapping("/{multiMeetingRoomSeq}")
    public ResponseEntity<Long> deleteMultiRoom(@PathVariable Long multiMeetingRoomSeq) throws NotFoundException{
        multiMeetingRoomService.deleteMultiMeetingRoom(multiMeetingRoomSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(multiMeetingRoomSeq);
    }
    /*
     * @return   MultiMeetingRoomRes dto
     * */
    @ApiOperation(value = "모든 단체 미팅방 리스트 가져오기", notes = "모든 단체 미팅방 리스트 가져오기")
    @GetMapping("/")
    public ResponseEntity<List<MultiMeetingRoomRes>> getAllMultiRoom() {
        return ResponseEntity.status(HttpStatus.OK).body(multiMeetingRoomService.findAllMultiMeetingRoom());
    }
    @ApiOperation(value = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기", notes = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기")
    @GetMapping("/{multiMeetingRoomSeq}")
    public ResponseEntity<MultiMeetingRoomRes> getMultiRoom(@PathVariable Long multiMeetingRoomSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(
                multiMeetingRoomService.findMultiMeetingRoom(multiMeetingRoomSeq)
        );
    }

    //add/remove user to/from multiMeetingRoom
    @ApiOperation(value = "단체 미팅방에 유저 추가하기", notes = "단체 미팅방에 유저를 추가하면서 +  해당 미팅방에 속해있는 유저들한테 웹소켓으로 정보(인원, 입장) 보냄")
    @PostMapping("/{multiMeetingRoomSeq}/{userSeq}")
    public ResponseEntity saveUserToMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException, MeetingRoomAlreadyFullException {
        multiMeetingRoomService.saveUserToMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        multiMeetingRoomService.sendToWebSocketAtJoin(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "단체 미팅방에서 유저 삭제", notes = "단체 미팅방에서 유저 삭제하면서 + 해당 미팅방에 속해있는 유저들한테 웹소켓으로 정보(인원, 퇴장) 보냄")
    @DeleteMapping("/{multiMeetingRoomSeq}/{userSeq}")
    public ResponseEntity removeUserFromMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException{
        multiMeetingRoomService.deleteUserFromMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @ApiOperation(value = "단체 미팅방 채팅 보내기", notes = "웹소켓으로 연결되어있는 채팅방 인원들한테 메세지를 보냄")
    @MessageMapping("/send-multi")
    public ResponseEntity<String> sendMultiChat(MultiWebSocketReq req) throws NotFoundException {
        multiMeetingRoomService.sendToWebSocket(req);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
