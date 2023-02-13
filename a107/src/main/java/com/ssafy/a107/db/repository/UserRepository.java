package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT CASE WHEN uf.user_male_seq = :userSeq THEN uf.user_female_seq ELSE uf.user_male_seq END as friend_seq " +
            "FROM user_friend uf " +
            "WHERE (uf.user_male_seq = :userSeq OR uf.user_female_seq = :userSeq)",
            nativeQuery = true)
    List<Long> findFriendsByUserSeq(@Param("userSeq") Long userSeq);


    @Query("select u from UserBlocked ub inner join User u on ub.user.seq = u.seq where ub.user.seq = ?1")
    List<User> findBlockedByUserSeq(Long userSeq);

}
