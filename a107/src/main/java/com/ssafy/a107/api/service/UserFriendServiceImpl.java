package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.UserFriend;
import com.ssafy.a107.db.repository.UserFriendRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFriendServiceImpl implements UserFriendService {

    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;

    /**
     * 친구 추가
     * @param userSeq: 추가하는 유저(나)의 userSeq
     * @param otherUserSeq: 친구로 추가할 유저(상대방)의 userSeq
     */
    @Override
    public Long addFriend(Long userSeq, Long otherUserSeq) throws NotFoundException {
        return userFriendRepository.save(UserFriend.builder()
            .user(userRepository.findById(userSeq)
                    .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
            .friend(userRepository.findById(otherUserSeq)
                    .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
            .build()).getSeq();
    }

    @Override
    public void deleteFriend(Long userSeq, Long otherUserSeq) throws NotFoundException{
        if(userRepository.existsById(userSeq)
        && userRepository.existsById(otherUserSeq)) {
            userFriendRepository.deleteByUserSeqAndFriendSeq(userSeq, otherUserSeq);
        }else throw new NotFoundException("Wrong User Seq!");
    }

}
