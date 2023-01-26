package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.BlockedUser;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.BlockedUserRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockedUserServiceImpl implements BlockedUserService{


    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    @Override
    public Long addBlockedUser(Long userSeq, Long targetUserSeq) throws NotFoundException {
        BlockedUser blockedUser = BlockedUser.builder()
                .user(userRepository.findById(userSeq)
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .target(userRepository.findById(targetUserSeq)
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .build();

        BlockedUser save = blockedUserRepository.save(blockedUser);

        return save.getSeq();
    }
}
