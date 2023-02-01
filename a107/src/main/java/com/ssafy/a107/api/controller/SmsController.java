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

        // 잘못된 요청 or 잘못된 양식
        if(smsReq.getPhoneNumber() == null || smsReq.getCode() != null
                || smsReq.getPhoneNumber().contains("-") || !smsReq.getPhoneNumber().startsWith("010")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String code = smsService.makeRandomCode();
        log.debug("Generated code: {}", code);

        // SMS 전송 정상 접수
        if(smsService.sendSmsToUser(smsReq.getPhoneNumber(), code).equals("2000")) {
            log.debug("Sent SMS to {}", smsReq.getPhoneNumber());
            smsService.createSms(smsReq, code.toString());
            log.debug("SMS info has been created in Redis.");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            log.debug("SMS failed to be sent.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "인증번호가 일치하는지 확인")
    @PostMapping("/check")
    public ResponseEntity<?> checkCode(@RequestBody SmsReq smsReq) throws NotFoundException {

        if(smsReq.getCode() == null) {
            log.debug("Code typed by user is null.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String phoneNumber = smsReq.getPhoneNumber();
        String codeFromRedis = smsService.getRecentCodeByPhoneNumber(phoneNumber);

        // 시간 초과 or 서버 에러
        if(codeFromRedis == null) {
            log.debug("Code from Redis is null.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.debug("Code from Redis: {}", codeFromRedis);
        log.debug("Code typed by user: {}", smsReq.getCode());

        // 인증번호가 일치하면
        if(smsService.matchCodes(codeFromRedis, smsReq.getCode())) {
            log.debug("Two codes match.");

            if(smsService.hasKey(phoneNumber)) {
                smsService.removeSms(phoneNumber);
                log.debug("Code for {} has been removed from Redis.", phoneNumber);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new SmsRes(smsReq));
        }

        // 일치하지 않으면
        log.debug("Two codes not match.");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
