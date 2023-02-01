package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.MultiMeetingRoomReq;
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
@RequestMapping("/api/multi-meeting")
@RequiredArgsConstructor
public class MultiMeetingRoomController {
    private final MultiMeetingRoomService multiMeetingRoomService;

    @ApiOperation(value = "새 단체 미팅방 생성하기",notes = "새 단체 미팅방 생성하기")
    @PostMapping("/")
    public ResponseEntity<?> makeMultiRoom(@RequestBody MultiMeetingRoomReq multiMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        Long newRoomSeq = multiMeetingRoomService.saveMultiMeetingRoom(multiMeetingRoomReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoomSeq);
    }
    @ApiOperation(value = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기", notes = "단체 미팅방 시퀀스로 단체 미팅방 정보 가져오기")
    @GetMapping("/{meetingRoomSeq}")
    public ResponseEntity<?> getMultiRoom(@PathVariable Long meetingRoomSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(
                multiMeetingRoomService.getMultiMeetingRoom(meetingRoomSeq)
        );
    }
    @ApiOperation(value = "모든 단체 미팅방 리스트 가져오기", notes = "모든 단체 미팅방 리스트 가져오기")
    @GetMapping("/list")
    public ResponseEntity<?> getAllMultiRoom(){
        return ResponseEntity.status(HttpStatus.OK).body(multiMeetingRoomService.findAllMultiMeetingRoom());
    }
    @ApiOperation(value = "단체 미팅방 삭제하기", notes = "단체 미팅방 삭제하기")
    @DeleteMapping("/{meetingRoomSeq}")
    public ResponseEntity<?> deleteMultiRoom(@PathVariable Long meetingRoomSeq) throws NotFoundException{
        multiMeetingRoomService.deleteMultiMeetingRoom(meetingRoomSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
