package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.MultyMeetingRoomReq;
import com.ssafy.a107.api.service.MultyMeetingRoomService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController("/api/multyroom")
public class MultyMeetingRoomController {
    private final MultyMeetingRoomService multyMeetingRoomService;

    @ApiOperation(value = "")
    @PostMapping("/new")
    public ResponseEntity<?> makeMultyRoom(MultyMeetingRoomReq multyMeetingRoomReq) throws NotFoundException {
        Long newRoomSeq = multyMeetingRoomService.saveMultyRoom(multyMeetingRoomReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoomSeq);
    }
    @ApiOperation(value = "")
    @GetMapping("/{roomSeq}")
    public ResponseEntity<?> getMultyRoom(@PathVariable Long roomSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(
                multyMeetingRoomService.getMultyRoom(roomSeq);
        );
    }
    @ApiOperation(value = "")
    @PostMapping("/{roomSeq}/join/{userSeq}")
    public ResponseEntity<?> joinMultyRoom(@PathVariable Long meetingRoomSeq, @PathVariable Long userSeq){
        multyMeetingRoomService.joinMultyRoom(Long meetingRoomSeq, Long userSeq);
        ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
