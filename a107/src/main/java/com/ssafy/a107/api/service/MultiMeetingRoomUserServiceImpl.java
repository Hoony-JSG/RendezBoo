package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.MultiMeetingRoomUser;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.MultiMeetingRoomUserRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MultiMeetingRoomUserServiceImpl implements MultiMeetingRoomUserService{

    private final MultiMeetingRoomUserRepository muRepo;
    private final UserRepository userRepo;
    private final MultiMeetingRoomRepository mRepo;
    @Transactional
    @Override
    public Long addUserToMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        return muRepo.save(MultiMeetingRoomUser.builder()
                .multiMeetingRoom(mRepo.findById(multiMeetingRoomSeq)
                        .orElseThrow(() -> new NotFoundException("Invalid Multi meeting room sequence!")))
                        .user(userRepo.findById(userSeq)
                                .orElseThrow(() -> new NotFoundException("Invalid user sequence!")))
                .build()).getSeq();
    }

    @Transactional
    @Override
    public void removeUserFromMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        if(!mRepo.existsById(multiMeetingRoomSeq))
            throw new NotFoundException("Invalid multi meeting room sequence!");
        else if(!userRepo.existsById(userSeq))
            throw new NotFoundException("Invalid user sequence!");
        muRepo.deleteByMultiMeetingRoomSeqAndUserSeq(multiMeetingRoomSeq, userSeq);
    }
}
