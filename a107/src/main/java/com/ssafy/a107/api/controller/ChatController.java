package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ChatReq;
import com.ssafy.a107.api.response.ChatRes;
import com.ssafy.a107.api.service.ChatService;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Chat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "채팅(친구 추가 이후) API", tags = {"Chat"})
@RestController
//@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/send")
    @ApiOperation("채팅 생성(보내기) - stomp")
    public ResponseEntity<ChatRes> sendChat(@RequestBody ChatReq req) throws NotFoundException{
//        log.debug("got message");
//        log.debug("message: " + req.getMessage());
//        log.debug("chatRoomSeq: " + req.getChatRoomSeq());
//        log.debug("senderSeq: " + req.getSenderSeq());
//        log.debug("receiverSeq: " + req.getReceiverSeq());


        String insertChat = chatService.insertChat(req);
        Chat chat = chatService.findBySeq(insertChat);

        ChatRes chatRes = new ChatRes(chat);
        sendingOperations.convertAndSend("/sub/chat/" + chatRes.getChatRoomSeq(), chatRes);


        return ResponseEntity.status(HttpStatus.CREATED).body(chatRes);
    }


    @GetMapping("/api/chat/{chatRoomSeq}")
    @ApiOperation("해당 채팅 방의 채팅 내역 불러오기")
    public ResponseEntity<List<ChatRes>> getChatByChatRoomSeq(@PathVariable Long chatRoomSeq) throws NotFoundException{
        List<Chat> chatList = chatService.findByChatRoomSeqOrderByCreatedAtDesc(chatRoomSeq);

        List<ChatRes> chatResList = chatList.stream()
                .map(ChatRes::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(chatResList);
    }
}
