package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
