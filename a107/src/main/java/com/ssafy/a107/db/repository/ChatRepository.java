package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository <Chat, Long> {

    Chat findTopByOrderByCreatedAtDesc();
}
