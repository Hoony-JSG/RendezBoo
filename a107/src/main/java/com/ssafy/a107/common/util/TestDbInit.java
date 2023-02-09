package com.ssafy.a107.common.util;

import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// 테스트용 유저 6명 추가하기
@Component
@RequiredArgsConstructor
public class TestDbInit {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private final TestDbInitService testDbInitService;

//    @PostConstruct
//    public void init() throws ParseException {
//        testDbInitService.userInit1();
//        testDbInitService.userInit2();
//        testDbInitService.userInit3();
//        testDbInitService.userInit4();
//        testDbInitService.userInit5();
//        testDbInitService.userInit6();
//    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class TestDbInitService {

        private final UserRepository userRepository;

        public void userInit1() throws ParseException {

            User user1 = User.builder()
                    .email("user1@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(true)
                    .birthday(format.parse("1997-10-19"))
                    .name("남자1번")
                    .mbti("ABCD")
                    .phoneNumber("123412341234")
                    .point(0L)
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user1);
        }

        public void userInit2() throws ParseException {

            User user2 = User.builder()
                    .email("user2@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(true)
                    .birthday(format.parse("1997-10-19"))
                    .name("남자2번")
                    .mbti("ABCD")
                    .phoneNumber("123412341234")
                    .point(0L)
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user2);
        }

        public void userInit3() throws ParseException {

            User user3 = User.builder()
                    .email("user3@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(true)
                    .birthday(format.parse("1997-10-19"))
                    .name("남자3번")
                    .mbti("ABCD")
                    .point(0L)
                    .phoneNumber("123412341234")
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user3);
        }

        public void userInit4() throws ParseException {

            User user4 = User.builder()
                    .email("user4@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(false)
                    .birthday(format.parse("1997-10-19"))
                    .name("여자1번")
                    .mbti("ABCD")
                    .phoneNumber("123412341234")
                    .point(0L)
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user4);
        }

        public void userInit5() throws ParseException {

            User user5 = User.builder()
                    .email("user5@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(false)
                    .birthday(format.parse("1997-10-19"))
                    .name("여자5번")
                    .mbti("ABCD")
                    .phoneNumber("123412341234")
                    .point(0L)
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user5);
        }

        public void userInit6() throws ParseException {

            User user6 = User.builder()
                    .email("user6@email.com")
                    .password("11111111")
                    .city("서울")
                    .gender(false)
                    .birthday(format.parse("1997-10-19"))
                    .name("여자6번")
                    .mbti("ABCD")
                    .phoneNumber("123412341234")
                    .point(0L)
                    .isAdmin(false)
                    .isValid(true)
                    .build();

            userRepository.save(user6);
        }
    }
}
