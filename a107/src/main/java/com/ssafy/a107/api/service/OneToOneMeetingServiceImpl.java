package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.OneToOneMeetingJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.OneToOneMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.SessionKeyProvider;
import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.OneToOneMeetingRoomRepository;
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
public class OneToOneMeetingServiceImpl implements OneToOneMeetingService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openVidu;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    private final OneToOneMeetingRoomRepository oneToOneMeetingRoomRepository;

    private final UserRepository userRepository;

    /**
     * 유저 시퀀스를 받아서 성별을 확인 후 상대 성별이 만든 미팅방이 있을 시 참여, 없을 시 새로운 미팅방 생성 후 해당 세션의 아이디와
     * 토큰을 컨트롤러로 전달
     *
     * @param oneToOneMeetingJoinReq 유저 시퀀스 필요
     * @return MeetingRoomRes에 세션아이디와 토큰 담김
     * @throws NotFoundException           유저가 없을 때
     * @throws OpenViduJavaClientException 오픈비두 에러
     * @throws OpenViduHttpException       오픈비두 에러
     */
    @Override
    @Transactional
    public MeetingRoomRes joinMatch(OneToOneMeetingJoinReq oneToOneMeetingJoinReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException {
        User user = userRepository.findById(oneToOneMeetingJoinReq.getUserSeq())
                .orElseThrow(() -> new NotFoundException("Wrong User Seq!"));
        OnetoOneMeetingRoom onetoOneMeetingRoom = null;
        List<OnetoOneMeetingRoom> list = null;
        Session session = null;
        // 남자가 1=true 여자가 0=false
        if (user.getGender()) {
            list = oneToOneMeetingRoomRepository.getOnetoOneMeetingRoomsByManSeqIsNullAndStatus((byte) 0);
        } else {
            list = oneToOneMeetingRoomRepository.getOnetoOneMeetingRoomsByWomanSeqIsNullAndStatus((byte) 0);
        }
        if (!list.isEmpty()) {
            // 리스트가 있으면 첫번째 세션 가져옴
            onetoOneMeetingRoom = list.get(0);
            session = openVidu.getActiveSession(onetoOneMeetingRoom.getSessionId());
            onetoOneMeetingRoom.changeStatus((byte) 1);

        } else {
            // 리스트 없으면 세션 생성
            Map<String, Object> params = Map.of("customSessionId", SessionKeyProvider.getSessionKey("OPENVIDU", "OTO"));
            SessionProperties properties = SessionProperties.fromJson(params).build();
            session = openVidu.createSession(properties);
            onetoOneMeetingRoom = new OnetoOneMeetingRoom();
            onetoOneMeetingRoom.createSession(session.getSessionId());
        }
        // 세션에 성별 기준으로 유저 추가
        if (user.getGender()) {
            onetoOneMeetingRoom.addMan(oneToOneMeetingJoinReq.getUserSeq());
        } else {
            onetoOneMeetingRoom.addWoman(oneToOneMeetingJoinReq.getUserSeq());
        }
        log.debug(onetoOneMeetingRoom.toString());
        oneToOneMeetingRoomRepository.save(onetoOneMeetingRoom);
        // 검사
        if (session == null) {
            throw new NotFoundException("Wrong Session");
        }
        // 커넥션용 토큰 생성
        ConnectionProperties connectionProperties = ConnectionProperties.fromJson(Map.of()).build();
        Connection connection = session.createConnection(connectionProperties);
        String token = connection.getToken();
        return new MeetingRoomRes(token);
    }

    @Override
    public List<OneToOneMeetingRoomRes> getOneToOneMeetingRooms(Byte status) {
        List<OneToOneMeetingRoomRes> list = oneToOneMeetingRoomRepository.getOnetoOneMeetingRoomsByStatus(status)
                .stream().map(OneToOneMeetingRoomRes::new).collect(Collectors.toList());
        return list;
    }

    @Override
    @Transactional
    public void closeMatch(Long meetingRoomSeq) throws NotFoundException {
        OnetoOneMeetingRoom room = oneToOneMeetingRoomRepository.findById(meetingRoomSeq)
                .orElseThrow(() -> new NotFoundException("Wrong Room Seq"));
        room.changeStatus((byte) 2);
        oneToOneMeetingRoomRepository.save(room);
    }
}
