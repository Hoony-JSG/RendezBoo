package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.MultiMeetingRoomUserService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "미팅방-유저 APi", tags = {"MultiMeetingRoomUser"})
@RestController
@RequestMapping("/api/multimeeting-user")
@RequiredArgsConstructor
public class MultiMeetingRoomUserController {
    private final MultiMeetingRoomUserService multiMeetingRoomUserService;

    @PostMapping("/{multiMeetingRoomSeq}/{userSeq}")
    @ApiOperation(value = "미팅방에 유저 더하기", notes = "미팅방에 유저 더하기")
    public ResponseEntity<?> addUserToMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException{
        multiMeetingRoomUserService.addUserToMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{multiMeetingRoomSeq}/{userSeq}")
    @ApiOperation(value = "미팅방에서 유저 삭제", notes = "미팅방에서 유저 삭제")
    public ResponseEntity<?> removeUserFromMultiMeetingRoom(@PathVariable Long multiMeetingRoomSeq, @PathVariable Long userSeq) throws NotFoundException{
        multiMeetingRoomUserService.removeUserFromMultiMeetingRoom(multiMeetingRoomSeq, userSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
