package com.ssafy.a107.db.repository.game;

import com.ssafy.a107.db.entity.game.GameOfDeath;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameOfDeathRepository extends MongoRepository<GameOfDeath, Long> {
}
