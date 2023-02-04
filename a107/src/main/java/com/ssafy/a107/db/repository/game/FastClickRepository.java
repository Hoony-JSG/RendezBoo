package com.ssafy.a107.db.repository.game;

import com.ssafy.a107.db.entity.game.FastClick;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FastClickRepository extends MongoRepository<FastClick, Long> {
}
