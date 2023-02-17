package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.StickCreateReq;
import com.ssafy.a107.api.request.StickReq;
import com.ssafy.a107.api.response.StickRes;
import com.ssafy.a107.api.service.StickService;
import com.ssafy.a107.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class StickController {

    private final StickService stickService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/stick/start")
    public void startLoveStick(@RequestBody StickCreateReq stickCreateReq) {
        StickRes stickRes = stickService.createStickOfLove(stickCreateReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + stickRes.getMultiMeetingRoomSeq(), stickRes);
    }

    @MessageMapping("/stick")
    public void runLoveStick(@RequestBody StickReq stickReq) throws NotFoundException {
        StickRes stickRes = stickService.runStickOfLove(stickReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + stickRes.getMultiMeetingRoomSeq(), stickRes);
    }
}
