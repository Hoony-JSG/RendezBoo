package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
}
