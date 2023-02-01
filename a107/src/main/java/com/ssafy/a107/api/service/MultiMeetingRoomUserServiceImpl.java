package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.MultiMeetingRoomUser;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.MultiMeetingRoomUserRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MultiMeetingRoomUserServiceImpl implements MultiMeetingRoomUserService{

    private final MultiMeetingRoomUserRepository multiMeetingRoomUserRepository;
    private final UserRepository userRepository;
    private final MultiMeetingRoomRepository multiMeetingRoomRepository;
    @Override
    public Long addUserToMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        return multiMeetingRoomUserRepository.save(MultiMeetingRoomUser.builder()
                .multiMeetingRoom(multiMeetingRoomRepository.findById(multiMeetingRoomSeq)
                        .orElseThrow(() -> new NotFoundException("Invalid User Seq!")))
                        .user(userRepository.findById(userSeq)
                                .orElseThrow(() -> new NotFoundException("Invalid Multi meeting room Seq!")))
                .build()).getSeq();
    }

    @Override
    public void removeUserFromMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        if(!multiMeetingRoomRepository.existsById(multiMeetingRoomSeq))
            throw new NotFoundException("Invalid multi meeting room sequence!");
        else if(!userRepository.existsById(userSeq))
            throw new NotFoundException("Invalid user sequence!");
        multiMeetingRoomUserRepository.deleteByMultiMeetingRoomSeqAndUserSeq(multiMeetingRoomSeq, userSeq);
    }
}
