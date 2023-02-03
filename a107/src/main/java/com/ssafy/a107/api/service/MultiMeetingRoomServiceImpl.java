package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.MultiMeetingRoomCreationReq;
import com.ssafy.a107.api.request.MultiMeetingRoomJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.api.response.MultiWebSocketRes;
import com.ssafy.a107.common.exception.NotFoundException;
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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /*
     *   1. Session Initialization, 2. Connection Creation
     * @return   MeetingRoomRes dto
     * */
    @Transactional
    @Override
    public MeetingRoomRes initializeSession(MultiMeetingRoomCreationReq multiMeetingRoomCreationReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        User user = userRepository.findById(multiMeetingRoomCreationReq.getUserSeq())
                .orElseThrow(()->new NotFoundException("Invalid User sequence!"));
        //세션 만들기
        Session session = openVidu.createSession();
        //멀티미팅방 데이터 생성
        MultiMeetingRoom multiMeetingRoom = MultiMeetingRoom.builder()
                .title(multiMeetingRoomCreationReq.getTitle())
                .status((byte)0)
                .sessionId(session.getSessionId())
                .build();
        //만들어진 멀티미팅망을 multi_meeting_room테이블에 저장
        Long roomSeq = multiMeetingRoomRepository.save(multiMeetingRoom).getSeq();

        //유저가 미팅방에 들어가는 사실을 multi_meeting_room_user맵핑 테이블에 저장
        multiMeetingRoomUserRepository.save(MultiMeetingRoomUser.builder()
                .user(user)
                .multiMeetingRoom(multiMeetingRoom).build());

        // 만들어진 세션에 connect
        ConnectionProperties connectionProperties = ConnectionProperties.fromJson(Map.of()).build();
        Connection connection = session.createConnection(connectionProperties);
        String token = connection.getToken();
        //initialize session은 원래 세션 아이디만을 리턴하지만
        //미팅방을 만든 유저 connect까지 해줬으므로 토큰 정보까지 리턴
        return new MeetingRoomRes(token);
    }
    @Transactional
    @Override
    public MeetingRoomRes createConnection(MultiMeetingRoomJoinReq multiMeetingRoomJoinReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException{
        String sessionId = multiMeetingRoomRepository.findById(multiMeetingRoomJoinReq.getMultiMeetingRoomSeq())
                .orElseThrow(()->new NotFoundException("Invalid multi meeting sequence!")).getSessionId();
        Session session = openVidu.getActiveSession(sessionId);

        ConnectionProperties connectionProperties = ConnectionProperties.fromJson(Map.of()).build();
        Connection connection = session.createConnection(connectionProperties);
        String token = connection.getToken();
        return new MeetingRoomRes(token);
    }

    /*
     * @return   MultiMeetingRoomRes dto
     * */
    @Override
    public MultiMeetingRoomRes findMultiMeetingRoom(Long multiMeetingRoomSeq) throws NotFoundException {
        return MultiMeetingRoomRes.builder()
                .roomEntity(multiMeetingRoomRepository.findById(multiMeetingRoomSeq)
                .orElseThrow(()->new NotFoundException("Invalid multiMeetingRoom sequence")))
                .maleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoomSeq, true))
                .femaleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoomSeq, false))
                .build();
    }
    @Override
    public List<MultiMeetingRoomRes> findAllMultiMeetingRoom(){
        return multiMeetingRoomRepository.findAll().stream()
                .map((multiMeetingRoom)->MultiMeetingRoomRes.builder()
                        .roomEntity(multiMeetingRoom)
                        .maleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoom.getSeq(), true))
                        .femaleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoom.getSeq(), false))
                        .build())
                .collect(Collectors.toList());
    }

    //미팅방 삭제
    @Transactional
    @Override
    public void deleteMultiMeetingRoom(Long meetingRoomSeq) throws NotFoundException {
        if(!multiMeetingRoomRepository.existsById(meetingRoomSeq)) throw new NotFoundException("Invalid multi meeting room sequence!");
        multiMeetingRoomUserRepository.deleteAllByMultiMeetingRoomSeq(meetingRoomSeq);
        multiMeetingRoomRepository.deleteById(meetingRoomSeq);
    }

    //미팅방에 유저 더하기, 삭제하기
    @Transactional
    @Override
    public Long saveUserToMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        return multiMeetingRoomUserRepository.save(MultiMeetingRoomUser.builder()
                .multiMeetingRoom(multiMeetingRoomRepository.findById(multiMeetingRoomSeq)
                        .orElseThrow(() -> new NotFoundException("Invalid Multi meeting room sequence!")))
                .user(userRepository.findById(userSeq)
                        .orElseThrow(() -> new NotFoundException("Invalid user sequence!")))
                .build()).getSeq();
    }

    @Transactional
    @Override
    public void deleteUserFromMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        if(!multiMeetingRoomRepository.existsById(multiMeetingRoomSeq))
            throw new NotFoundException("Invalid multi meeting room sequence!");
        else if(!userRepository.existsById(userSeq))
            throw new NotFoundException("Invalid user sequence!");
        multiMeetingRoomUserRepository.deleteByMultiMeetingRoomSeqAndUserSeq(multiMeetingRoomSeq, userSeq);
    }

//    multimeetingroom 세션 참가 시 웹소켓 연결 되어있는 클라이언트에게 메세지 보내는 기능
    @Override
    public void sendToWebSocketAtJoin(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException {
        if(!multiMeetingRoomRepository.existsById(multiMeetingRoomSeq))
            throw new NotFoundException("Invalid multi meeting room sequence!");
        else if(!userRepository.existsById(userSeq))
            throw new NotFoundException("Invalid user sequence!");

        MultiWebSocketRes.MultiJoinRes multiJoinRes = MultiWebSocketRes.MultiJoinRes.builder()
                .senderSeq(userSeq)
                .multiMeetingRoomSeq(multiMeetingRoomSeq)
                .flag(MultiWebSocketRes.MultiWebSocketFlag.JOIN)
                .maleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoomSeq, true))
                .femaleNum(multiMeetingRoomRepository.countByMultiMeetingRoomSeqAndGender(multiMeetingRoomSeq, false))
                .createdAt(LocalDateTime.now())
                .build();

//        미팅 룸 번호 구독 주소에 메세지 보냄
        simpMessageSendingOperations.convertAndSend("/sub/" + multiJoinRes.getMultiMeetingRoomSeq(), multiJoinRes);
    }




}
