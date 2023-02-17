package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.MultiMeetingRoomChat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MultiMeetingRoomChatRepository extends MongoRepository<MultiMeetingRoomChat, Long> {
    List<MultiMeetingRoomChat> findAllByMultiMeetingRoomSeqOrderByCreatedAtDesc(Long multiMeetingRoomSeq);
    
}
