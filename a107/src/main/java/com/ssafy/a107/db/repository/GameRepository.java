package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
