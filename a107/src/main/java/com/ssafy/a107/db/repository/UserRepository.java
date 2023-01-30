package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("select u from UserFriend uf inner join User u where uf.user.seq = ?1")
    List<User> findFriendByUserSeq(Long userSeq);

    @Query("select u from UserBlocked ub inner join User u where ub.user.seq = ?1")
    List<User> findBlockedByUserSeq(Long userSeq);

}
