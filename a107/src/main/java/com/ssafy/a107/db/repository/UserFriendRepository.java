package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

//    @Modifying
//    @Transactional
//    void deleteByUserSeqAndFriendSeq(Long userSeq, Long friendSeq);
    boolean existsByUserMaleSeqAndUserFemaleSeq(Long userMaleSeq, Long userFemaleSeq);

    UserFriend findByUserMaleSeqAndUserFemaleSeq(Long userMaleSeq, Long userFemaleSeq);

    @Modifying
    @Transactional
    void deleteByUserMaleSeqAndUserFemaleSeq(Long userMaleSeq, Long userFemaleSeq);
}
