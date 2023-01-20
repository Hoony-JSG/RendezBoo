package com.ssafy.a107.db.repository;


import com.ssafy.a107.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("저장 테스트")
    public void saveTest() {
        User user = User.builder()
                .email("test@test.com")
                .password("pass")
                .city("seoul")
                .gender(true)
                .phoneNumber("01012341234")
                .name("홍길동")
                .profileImagePath("abc.jpg")
                .MBTI("ENTJ")
                .point(1000L)
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertEquals("test@test.com", savedUser.getEmail());
    }
}
