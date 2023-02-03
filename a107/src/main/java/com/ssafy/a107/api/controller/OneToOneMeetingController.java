package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.service.OneToOneMeetingService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "일대일 미팅방 관련 컨트롤러", tags = {"OneToOneMeeting"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/onetoone")
public class OneToOneMeetingController {

    private final OneToOneMeetingService oneToOneMeetingService;

    @ApiOperation("유저 시퀀스 기반으로 일대일 매칭을 신청하면 토큰을 전달")
    @PostMapping("/{userSeq}")
    public ResponseEntity<?> joinMatch(@PathVariable Long userSeq) throws OpenViduJavaClientException, OpenViduHttpException, NotFoundException {
        MeetingRoomRes meetingRoomRes = oneToOneMeetingService.joinMatch(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(meetingRoomRes);
    }

    @ApiOperation("일대일 매칭의 종료")
    @PutMapping("")
    public ResponseEntity<?> closeMatch() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
