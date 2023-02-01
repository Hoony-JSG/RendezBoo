package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.MultiMeetingRoomReq;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.SessionKeyProvider;
import com.ssafy.a107.db.entity.MultiMeetingRoom;
import com.ssafy.a107.db.entity.MultiMeetingRoomUser;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.MultiMeetingRoomUserRepository;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.UserRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiMeetingRoomServiceImpl implements MultiMeetingRoomService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openVidu;
    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    private final MultiMeetingRoomRepository multiMeetingRoomRepository;
    private final MultiMeetingRoomUserRepository multiMeetingRoomUserRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long saveMultiMeetingRoom(MultiMeetingRoomReq multiMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        log.debug(multiMeetingRoomReq.toString());
        User user = userRepository.findById(multiMeetingRoomReq.getUserSeq())
                .orElseThrow(()->new NotFoundException("Invalid User sequence!"));
        log.debug(user.toString());
        Map<String, Object> params = Map.of("customSessionId", SessionKeyProvider.getSessionKey("OPENVIDU","MULTI"));
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openVidu.createSession(properties);
        MultiMeetingRoom multiMeetingRoom = MultiMeetingRoom.builder()
                .title(multiMeetingRoomReq.getTitle())
                .status((byte)0).build();
        multiMeetingRoom.createSession(session.getSessionId());

        Long roomSeq = multiMeetingRoomRepository.save(multiMeetingRoom).getSeq();
        multiMeetingRoomUserRepository.save(MultiMeetingRoomUser.builder()
                .user(user)
                .multiMeetingRoom(multiMeetingRoom).build());
        return roomSeq;
    }
    @Override
    public MultiMeetingRoomRes getMultiMeetingRoom(Long roomSeq) throws NotFoundException {
        int maleNum=0, femaleNum=0;
        //
        return new MultiMeetingRoomRes(multiMeetingRoomRepository.findById(roomSeq)
                .orElseThrow(()->new NotFoundException("Invalid multiMeetingRoom sequence")),
                maleNum, femaleNum
        );
    }
    @Override
    public List<MultiMeetingRoomRes> findAllMultiMeetingRoom(){
        return multiMeetingRoomRepository.findAll().stream()
                .map((multimeetingroom)->new MultiMeetingRoomRes(multimeetingroom,
                        0,//multiMeetingRoomUserRepository.countMaleByMeetingRoomSeq(multimeetingroom.getSeq()),
                        0//multiMeetingRoomUserRepository.countFemaleByMeetingRoomSeq(multimeetingroom.getSeq())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMultiMeetingRoom(Long meetingRoomSeq) throws NotFoundException {
        if(!multiMeetingRoomRepository.existsById(meetingRoomSeq)) throw new NotFoundException("Invalid multi meeting room sequence!");
        //meetingRoomSeq가 일치하는 multiMeetingRoomUser 데이터들을 먼저 삭제해야 한다
        multiMeetingRoomRepository.deleteById(meetingRoomSeq);
    }

}
