package com.ssafy.a107.api.service;

import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface OneToOneMeetingService {
    public MeetingRoomRes joinMatch(Long userSeq) throws NotFoundException, OpenViduJavaClientException, OpenViduHttpException;
}
