package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.MultiMeetingRoomCreationReq;
import com.ssafy.a107.api.request.MultiMeetingRoomJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.service.MultiMeetingRoomService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @ApiOperation(value = "새 단체 미팅방 생성하기",notes = "1. Initialize a Session")
    @PostMapping("/")
    public ResponseEntity<?> initializeMultiRoom(@RequestBody MultiMeetingRoomCreationReq multiMeetingRoomCreationReq)
            throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        MeetingRoomRes meetingRoomRes = multiMeetingRoomService.initializeSession(multiMeetingRoomCreationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingRoomRes);
    }

    @ApiOperation(value = "단체 미팅방과 연결 수립하기", notes = "2. Create a Connection")
    @PostMapping("/join")
    public ResponseEntity<?> connectMultiRoom(@RequestBody MultiMeetingRoomJoinReq multiMeetingRoomJoinReq)
            throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException{
        MeetingRoomRes meetingRoomRes = multiMeetingRoomService.createConnection(multiMeetingRoomJoinReq);
        return ResponseEntity.status(HttpStatus.OK).body(meetingRoomRes);
    }

    @ApiOperation(value = "단체 미팅방 삭제하기", notes = "단체 미팅방 삭제하기")
    @DeleteMapping("/{multiMeetingRoomSeq}")
    public ResponseEntity<?> deleteMultiRoom(@PathVariable Long multiMeetingRoomSeq) throws NotFoundException{
        multiMeetingRoomService.deleteMultiMeetingRoom(multiMeetingRoomSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    /*
     * @return   MultiMeetingRoomRes dto
     * */
    @ApiOperation(value = "모든 단체 미팅방 리스트 가져오기", notes = "모든 단체 미팅방 리스트 가져오기")
    @GetMapping("/")
    public ResponseEntity<?> getAllMultiRoom() {
        return ResponseEntity.status(HttpStatus.OK).body(multiMeetingRoomService.findAllMultiMeetingRoom());
    }
    @ApiOperation(value = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기", notes = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기")
    @GetMapping("/{multiMeetingRoomSeq}")
    public ResponseEntity<?> getMultiRoom(@PathVariable Long multiMeetingRoomSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(
                multiMeetingRoomService.findMultiMeetingRoom(multiMeetingRoomSeq)
        );
    }

    //add/remove user to/from multiMeetingRoom
    @ApiOperation(value = "단체 미팅방에 유저 추가하기", notes = "단체 미팅방에 유저 추가하기")
    @PostMapping("/{multiMeetingRoomSeq}/{userSeq}")
    public ResponseEntity<?> saveUserToMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException{
        multiMeetingRoomService.saveUserToMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @ApiOperation(value = "단체 미팅방에서 유저 삭제", notes = "단체 미팅방에서 유저 삭제하기")
    @DeleteMapping("/{multiMeetingRoomSeq}/{userSeq}")
    public ResponseEntity<?> removeUserFromMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException{
        multiMeetingRoomService.deleteUserFromMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
