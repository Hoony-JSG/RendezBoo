package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.SmsException;

public interface SmsService {

    void createSms(SmsReq smsReq, String code);
    String getRecentCodeByPhoneNumber(String phoneNumber);
    void removeSms(String phoneNumber) throws NotFoundException;
    void matchCodes(String sentCode, String typedCode) throws ConflictException;
    String makeRandomCode();
    void sendSmsToUser(String phoneNumber, String code) throws SmsException;
    void checkSmsReq(SmsReq smsReq) throws BadRequestException;
    void checkCode(String code, String from) throws BadRequestException, SmsException;
}
