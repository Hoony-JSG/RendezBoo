package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.BlockedUser;
import com.ssafy.a107.db.entity.Friend;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.BlockedUserRepository;
import com.ssafy.a107.db.repository.FriendRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockedUserServiceImpl implements BlockedUserService{


    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    @Override
    public void addBlockedUser(Long userSeq, Long targetUserSeq) {
        User user = userRepository.findBySeq(userSeq);
        User targetUser = userRepository.findBySeq(targetUserSeq);

        BlockedUser blockedUser = BlockedUser.builder()
                .user(user)
                .target(targetUser)
                .build();

        blockedUserRepository.save(blockedUser);
    }
}
