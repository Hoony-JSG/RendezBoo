package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.OneToOneMeetingJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.OneToOneMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.util.List;

public interface OneToOneMeetingService {
    public MeetingRoomRes joinMatch(OneToOneMeetingJoinReq oneToOneMeetingJoinReq)
            throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;

    List<OneToOneMeetingRoomRes> getOneToOneMeetingRooms(Byte status);

    void closeMatch(Long meetingRoomSeq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;

    void startOneToOneMeeting(Long meetingRoomSeq);

    void deleteGlasses(Long meetingRoomSeq);

    void deleteMasks(Long meetingRoomSeq);
}
