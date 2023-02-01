package com.ssafy.a107.api.response;

import com.ssafy.a107.api.request.SmsReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsRes {

    private String phoneNumber;
    private String code;

    public SmsRes(SmsReq smsReq) {
        this.phoneNumber = smsReq.getPhoneNumber();
        this.code = smsReq.getCode();
    }
}
