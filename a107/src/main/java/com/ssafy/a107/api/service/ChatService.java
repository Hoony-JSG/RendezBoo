package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ChatReq;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Chat;

import java.util.List;

public interface ChatService {

    String insertChat(ChatReq chatReq) throws NotFoundException;

    Chat findBySeq(String seq) throws NotFoundException;

    List<Chat> findByChatRoomSeqOrderByCreatedAtDesc(Long chatRoomSeq) throws NotFoundException;

}
