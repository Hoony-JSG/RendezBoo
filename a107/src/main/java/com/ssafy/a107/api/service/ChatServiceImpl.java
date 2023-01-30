package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ChatReq;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Chat;
import com.ssafy.a107.db.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public String insertChat(ChatReq chatReq) throws NotFoundException {
        Chat chat = Chat.builder()
                .chatRoomSeq(chatReq.getChatRoomSeq())
                .receiverSeq(chatReq.getReceiverSeq())
                .senderSeq(chatReq.getSenderSeq())
                .message(chatReq.getMessage())
                .build();
        Chat insert = chatRepository.insert(chat);
        return insert.getSeq();

    }

    @Override
    public Chat findBySeq(String seq) throws NotFoundException {
        return chatRepository.findBySeq(seq);
    }

    @Override
    public List<Chat> findByChatRoomSeqOrderByCreatedAtDesc(String chatRoomSeq) {
        return chatRepository.findByChatRoomSeqOrderByCreatedAtDesc(chatRoomSeq);
    }
}
