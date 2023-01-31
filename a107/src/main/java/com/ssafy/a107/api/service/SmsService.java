package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface SmsService {

    Long createSms(SmsReq smsReq, String code);
    SmsRes getRecentCodeByEmail(String email) throws NotFoundException;
    Boolean matchCodes(String sentCode, String typedCode);
}
