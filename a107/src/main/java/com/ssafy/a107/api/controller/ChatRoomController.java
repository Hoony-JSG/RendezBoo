package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ChatRoomCreateReq;
import com.ssafy.a107.api.response.ChatRoomRes;
import com.ssafy.a107.api.service.ChatRoomService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "채팅방(친구 추가 이후) API", tags = {"ChatRoom"})
@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "유저의 채팅방 정보 조회", notes = "유저의 채팅방 정보 조회")
    public ResponseEntity<List<ChatRoomRes>> getAllRoomByUserSeq(@PathVariable Long userSeq) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomService.getAllRoomByUserSeq(userSeq));
    }

    @PostMapping("")
    @ApiOperation(value = "채팅방 생성", notes = "채팅방 생성")
    public ResponseEntity<Long> createChatRoom(@RequestBody ChatRoomCreateReq chatRoomCreateReq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.createChatRoom(chatRoomCreateReq));
    }

}
