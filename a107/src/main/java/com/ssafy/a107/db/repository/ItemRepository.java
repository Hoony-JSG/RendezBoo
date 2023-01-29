package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from UserItem ui inner join Item i where ui.user.seq = ?1")
    List<Item> findItemsByUserSeq(Long userSeq);
}
