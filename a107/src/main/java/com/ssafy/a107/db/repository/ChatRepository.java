package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository <Chat, Long> {

    Chat findTopByOrderByCreatedAtDesc();
    
//    채팅방 번호로 찾아서 생성일 내림차순으로 반환
    List<Chat> findByChatRoomSeqOrderByCreatedAtDesc(String chatRoomSeq);

    Chat findBySeq(String seq);
}
