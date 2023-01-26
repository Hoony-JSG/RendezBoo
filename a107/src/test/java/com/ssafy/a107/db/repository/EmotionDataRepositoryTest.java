package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import com.ssafy.a107.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class EmotionDataRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OneToOneMeetingRoomRepository roomRepository;
    @Autowired
    private EmotionDataRepository emotionDataRepository;
    @Test
    public void test(){
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
        List<EmotionData> list2 = new ArrayList<>();
        for(int i=0; i<MAX; i++) {
            d[i] = r.nextDouble();
            OnetoOneMeetingRoom o = new OnetoOneMeetingRoom();
            o.addMan(new Long(i));
            o.addWoman(new Long(i));
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
            list2.add(e[i]);
        }
        List<EmotionData> list1 = emotionDataRepository.findTop10ByUserSeq(user.getSeq());
        for(int i=0; i<10; i++){
            Assertions.assertEquals(list1.get(i).getAnger(), list2.get(i).getAnger());
        }

    }
}
