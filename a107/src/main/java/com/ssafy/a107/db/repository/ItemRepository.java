package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
