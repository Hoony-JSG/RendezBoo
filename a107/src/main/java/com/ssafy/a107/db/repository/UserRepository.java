package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    public Optional<User> findByEmail(String email);

    User findBySeq (Long seq);



}
