package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;

public interface MultiMeetingRoomUserService {
    Long addUserToMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException;
    void removeUserFromMultiMeetingRoom(Long multiMeetingRoomSeq, Long userSeq) throws NotFoundException;
}
