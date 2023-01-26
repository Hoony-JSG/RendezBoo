package com.ssafy.a107.repository;

import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void joinUserTest(){
        String myEmail = "wx776654@gmail.com";
        String myMBTI = "ENTP";

        User user = User.builder()
                .email(myEmail)
                .password("123456")
                .city("Seoul")
                .gender(false)
                .phoneNumber("010-0000-0000")
                .name("Hongju")
                .profileImagePath("/~")
                .MBTI(myMBTI)
                .build();

        User joinUser = userRepository.save(user);
        Assertions.assertEquals(joinUser.getEmail(), myEmail);
        Assertions.assertEquals(joinUser.getMBTI(), myMBTI);

    }
}
