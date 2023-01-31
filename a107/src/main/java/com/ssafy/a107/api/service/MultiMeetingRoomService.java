package com.ssafy.a107.api.service;


import com.ssafy.a107.api.request.MultyMeetingRoomReq;
import com.ssafy.a107.api.response.MultiMeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface MultiMeetingRoomService {

    Long saveMultiMeetingRoom(MultyMeetingRoomReq multyMeetingRoomReq);
    MultiMeetingRoomRes getMultiMeetingRoom(Long roomSeq) throws NotFoundException;

    Long joinMultiMeetingRoom(Long meetingRoomSeq, Long userSeq) throws NotFoundException;
}
