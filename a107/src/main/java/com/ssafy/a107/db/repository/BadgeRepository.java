package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    @Query("select b from UserBadge ub inner join Badge b where ub.user.seq = ?1")
    List<Badge> findBadgesByUserSeq(Long userSeq);
}
