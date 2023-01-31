package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.api.service.SmsService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("SMS API")
@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService smsService;

    @ApiOperation(value = "사용자에게 인증번호 전송", notes = "번호는 01012345678 형태로 입력")
    @PostMapping("/send")
    public ResponseEntity<?> sendOne(@RequestBody SmsReq smsReq) {

        // 잘못된 요청
        if(smsReq.getEmail() == null || smsReq.getPhoneNumber() == null || smsReq.getCode() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String code = smsService.makeRandomCode();

        // SMS 전송 정상 접수
        if(smsService.sendSmsToUser(smsReq.getPhoneNumber(), code).equals("2000")) {
            smsService.createSms(smsReq, code.toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            log.debug("SMS 전송 접수 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "인증번호가 일치하는지 확인")
    @PostMapping("/check")
    public ResponseEntity<?> checkCode(@RequestBody SmsReq smsReq) throws NotFoundException {

        SmsRes smsRes = smsService.getRecentCodeByEmail(smsReq.getEmail());

        // 인증번호가 일치하면
        if(smsService.matchCodes(smsRes.getCode(), smsReq.getCode())) {
            return ResponseEntity.status(HttpStatus.OK).body(smsRes);
        }

        // 일치하지 않으면
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
