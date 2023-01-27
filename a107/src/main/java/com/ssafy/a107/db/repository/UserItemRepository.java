package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
}
