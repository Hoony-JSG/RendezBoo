package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.OneToOneMeetingJoinReq;
import com.ssafy.a107.api.response.MeetingRoomRes;
import com.ssafy.a107.api.response.OneToOneMeetingRoomRes;
import com.ssafy.a107.api.service.OneToOneMeetingService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "일대일 미팅방 관련 컨트롤러", tags = {"OneToOneMeeting"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/onetoone")
public class OneToOneMeetingController {

    private final OneToOneMeetingService oneToOneMeetingService;

    /**
     * 1. Session Initialization, 2. Connection Creation
     *
     * @return MeetingRoomRes dto
     */
    @ApiOperation("유저 시퀀스 기반으로 일대일 매칭을 신청하면 토큰을 전달")
    @PostMapping("")
    public ResponseEntity<?> joinMatch(@RequestBody OneToOneMeetingJoinReq oneToOneMeetingJoinReq) throws OpenViduJavaClientException, OpenViduHttpException, NotFoundException {
        MeetingRoomRes meetingRoomRes = oneToOneMeetingService.joinMatch(oneToOneMeetingJoinReq);
        return ResponseEntity.status(HttpStatus.OK).body(meetingRoomRes);
    }


    @ApiOperation("관리자 전용 -- 전체 일대일 미팅방 정보")
    @GetMapping("/{status}")
    public ResponseEntity<?> getOneToOneMeetingRooms(@PathVariable Byte status) {
        List<OneToOneMeetingRoomRes> list = oneToOneMeetingService.getOneToOneMeetingRooms(status);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @ApiOperation("일대일 매칭의 종료")
    @DeleteMapping("/{meetingRoomSeq}")
    public ResponseEntity<?> closeMatch(@PathVariable Long meetingRoomSeq) throws NotFoundException {
        oneToOneMeetingService.closeMatch(meetingRoomSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
