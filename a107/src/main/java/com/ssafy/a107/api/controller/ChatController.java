package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ChatReq;
import com.ssafy.a107.api.response.ChatRes;
import com.ssafy.a107.api.service.ChatServiceImpl;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Chat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "채팅(친구 추가 이후) API", tags = {"Chat"})
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    private final SimpMessageSendingOperations sendingOperations;



    @PostMapping("/send")
    @ApiOperation("채팅 생성(보내기) - stomp")
    public ResponseEntity<?> sendChat(@RequestBody ChatReq req) throws NotFoundException{

        String insertChat = chatService.insertChat(req);
        Chat chat = chatService.findBySeq(insertChat);

        ChatRes chatRes = new ChatRes(chat);
        sendingOperations.convertAndSend("/sub/" + chatRes.getChatRoomSeq(), chatRes);


        return ResponseEntity.status(HttpStatus.CREATED).body(chatRes);
    }



//    @PostMapping("/insert")
//    @ApiOperation("채팅 생성(보내기) - 테스트")
//    public ResponseEntity<?> createChat(@RequestBody ChatReq req){
//        try {
//            String insertChat = chatService.insertChat(req);
//            Chat chat = chatService.findBySeq(insertChat);
//
//            ChatRes chatRes = new ChatRes(chat);
//
//            sendingOperations.convertAndSend("/sub/" + req.getChatRoomSeq(), req);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(chatRes);
//
//        } catch (NotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    @GetMapping("/{chatRoomSeq}")
    @ApiOperation("해당 채팅 방의 채팅 내역 불러오기")
    public ResponseEntity<?> getChatByChatRoomSeq(@PathVariable Long chatRoomSeq) throws NotFoundException{
        List<Chat> chatList = chatService.findByChatRoomSeqOrderByCreatedAtDesc(chatRoomSeq);

        List<ChatRes> chatResList = chatList.stream()
                .map(ChatRes::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(chatResList);

    }

    
}
