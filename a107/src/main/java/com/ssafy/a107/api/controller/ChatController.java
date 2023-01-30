package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ChatReq;
import com.ssafy.a107.api.service.ChatServiceImpl;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Chat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "채팅(친구 추가 이후) API", tags = {"Chat"})
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    @PostMapping("/insert")
    @ApiOperation("채팅 생성(보내기)")
    public ResponseEntity<?> createChat(@RequestBody ChatReq req){
        try {
            String insertChat = chatService.insertChat(req);
            Chat chat = chatService.findBySeq(insertChat);
            return ResponseEntity.status(HttpStatus.CREATED).body(chat);

        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{chatRoomSeq}")
    @ApiOperation("해당 채팅 방의 채팅 내역 불러오기")
    public ResponseEntity<?> getChatByChatRoomSeq(@PathVariable Long chatRoomSeq) throws NotFoundException{
        List<Chat> chatList = chatService.findByChatRoomSeqOrderByCreatedAtDesc(chatRoomSeq);
        return ResponseEntity.status(HttpStatus.OK).body(chatList);

    }



}
