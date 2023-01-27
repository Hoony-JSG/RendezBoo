package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.UserBlocked;
import com.ssafy.a107.db.repository.UserBlockedRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBlockedServiceImpl implements UserBlockedService {


    private final UserRepository userRepository;
    private final UserBlockedRepository userBlockedRepository;
    @Override
    public Long addBlockedUser(Long userSeq, Long targetUserSeq) throws NotFoundException {
        UserBlocked blockedUser = UserBlocked.builder()
                .user(userRepository.findById(userSeq)
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .target(userRepository.findById(targetUserSeq)
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .build();

        UserBlocked save = userBlockedRepository.save(blockedUser);

        return save.getSeq();
    }
}
