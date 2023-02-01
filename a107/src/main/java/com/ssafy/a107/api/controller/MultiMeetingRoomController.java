package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.MultyMeetingRoomReq;
import com.ssafy.a107.api.service.MultiMeetingRoomService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController("/api/multi-room")
public class MultiMeetingRoomController {
    private final MultiMeetingRoomService multyMeetingRoomService;

    @ApiOperation(value = "")
    @PostMapping("/")
    public ResponseEntity<?> makeMultiRoom(MultyMeetingRoomReq multyMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        Long newRoomSeq = multyMeetingRoomService.saveMultiMeetingRoom(multyMeetingRoomReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoomSeq);
    }
    @ApiOperation(value = "")
    @GetMapping("/{meetingRoomSeq}")
    public ResponseEntity<?> getMultiRoom(@PathVariable Long meetingRoomSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(
                multyMeetingRoomService.getMultiMeetingRoom(meetingRoomSeq)
        );
    }
    @ApiOperation(value = "")
    @PostMapping("/{meetingRoomSeq}/join/{userSeq}")
    public ResponseEntity<?> joinMultiRoom(@PathVariable Long meetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException {
        multyMeetingRoomService.joinMultiMeetingRoom(meetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
