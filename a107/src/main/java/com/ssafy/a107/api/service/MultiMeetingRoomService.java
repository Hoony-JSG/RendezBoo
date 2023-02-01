package com.ssafy.a107.api.service;


import com.ssafy.a107.api.request.MultiMeetingRoomReq;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.util.List;

public interface MultiMeetingRoomService {

    Long saveMultiMeetingRoom(MultiMeetingRoomReq multiMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;
    MultiMeetingRoomRes getMultiMeetingRoom(Long roomSeq) throws NotFoundException;
//    Long joinMultiMeetingRoom(Long meetingRoomSeq, Long userSeq) throws NotFoundException;
    List<MultiMeetingRoomRes> findAllMultiMeetingRoom();
}
