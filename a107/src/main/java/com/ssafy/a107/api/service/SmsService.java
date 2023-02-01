package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface SmsService {

    void createSms(SmsReq smsReq, String code);
    String getRecentCodeByPhoneNumber(String phoneNumber) throws NotFoundException;
    void removeSms(String phoneNumber);
    boolean hasKey(String phoneNumber);
    Boolean matchCodes(String sentCode, String typedCode);
    String makeRandomCode();
    String sendSmsToUser(String phoneNumber, String code);
}
