package com.ssafy.a107.api.service;


import com.ssafy.a107.api.request.MultyMeetingRoomReq;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface MultiMeetingRoomService {

    Long saveMultiMeetingRoom(MultyMeetingRoomReq multyMeetingRoomReq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;
    MultiMeetingRoomRes getMultiMeetingRoom(Long roomSeq) throws NotFoundException;

    Long joinMultiMeetingRoom(Long meetingRoomSeq, Long userSeq) throws NotFoundException;
}
