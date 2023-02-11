package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.BadgeCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeConditionRepository extends JpaRepository<BadgeCondition, Long> {

    Optional<BadgeCondition> findBadgeConditionByUserSeq(Long userSeq);
}
