package com.ssafy.a107.api.service;


import com.ssafy.a107.api.request.MultiMeetingRoomCreationReq;
import com.ssafy.a107.api.request.MultiMeetingRoomJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.util.List;

public interface MultiMeetingRoomService {

    MeetingRoomRes initializeSession(MultiMeetingRoomCreationReq multiMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;
    MeetingRoomRes createConnection(MultiMeetingRoomJoinReq multiMeetingRoomJoinReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;
    void deleteMultiMeetingRoom(Long meetingRoomSeq) throws NotFoundException;


    MultiMeetingRoomRes findMultiMeetingRoom(Long roomSeq) throws NotFoundException;
    List<MultiMeetingRoomRes> findAllMultiMeetingRoom();

    Long saveUserToMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException;
    void deleteUserFromMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException;
}
