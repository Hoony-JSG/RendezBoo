package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.FriendReq;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.UserFriend;
import com.ssafy.a107.db.repository.UserFriendRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFriendServiceImpl implements UserFriendService {

    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;

    /**
     * 친구 추가
     */
    @Override
    public Long addFriend(FriendReq friendReq) throws NotFoundException {
        Long userMaleSeq = friendReq.getUserMaleSeq();
        Long userFemaleSeq = friendReq.getUserFemaleSeq();

        if (userFriendRepository.existsByUserMaleSeqAndUserFemaleSeq(userMaleSeq, userFemaleSeq)) {
            return userFriendRepository.findByUserMaleSeqAndUserFemaleSeq(userMaleSeq, userFemaleSeq).getSeq();
        }

        return userFriendRepository.save(UserFriend.builder()
            .userMale(userRepository.findById(userMaleSeq)
                    .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
            .userFemale(userRepository.findById(userFemaleSeq)
                    .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
            .build()).getSeq();
    }

    @Override
    public void deleteFriend(FriendReq friendReq) throws NotFoundException{

        Long userMaleSeq = friendReq.getUserMaleSeq();
        Long userFemaleSeq = friendReq.getUserFemaleSeq();

        if(userRepository.existsById(userMaleSeq) && userRepository.existsById(userFemaleSeq)){
            userFriendRepository.deleteByUserMaleSeqAndUserFemaleSeq(userMaleSeq, userFemaleSeq);
        }else throw new NotFoundException("Wrong User Seq!");
    }

}
