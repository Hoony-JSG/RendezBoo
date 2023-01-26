package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import com.ssafy.a107.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Random;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class EmotionDataRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OneToOneMeetingRoomRepository roomRepository;
    @Autowired
    private EmotionDataRepository emotionDataRepository;


    @Test
    public void 유저가_유발한_감정_테스트(){
        User user = User.builder()
                .email("email")
                .password("password")
                .city("Seoul")
                .gender(false)
                .phoneNumber("010-0000-0000")
                .name("Hongju")
                .profileImagePath("/hahaha")
                .MBTI("ENTP")
                .point(100L).build();
        userRepository.save(user);
        final int MAX = 20;
        Random r = new Random();
        EmotionData[] e = new EmotionData[MAX];
        double[] d = new double[MAX];
        for(int i=0; i<MAX; i++) {
            d[i] = r.nextDouble();
            OnetoOneMeetingRoom o = new OnetoOneMeetingRoom();
            roomRepository.save(o);
            e[i] = EmotionData.builder()
                    .user(user)
                    .meetingRoom(o)
                    .anger(d[i])
                    .contempt(d[i])
                    .disgust(d[i])
                    .fear(d[i])
                    .happiness(d[i])
                    .neutral(d[i])
                    .sadness(d[i])
                    .surprise(d[i]).build();
            emotionDataRepository.save(e[i]);
        }
        double avg = Arrays.stream(d).average().getAsDouble();
        EmotionData eResult = emotionDataRepository.getAVGExpressionDataByUserSeq(user.getSeq()).get();
        Assertions.assertEquals(avg, eResult.getAnger());
    }

}
