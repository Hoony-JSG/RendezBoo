package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Sms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsRes {

    private String email;
    private String phoneNumber;
    private String code;

    public SmsRes(Sms sms) {
        this.email = sms.getEmail();
        this.phoneNumber = sms.getPhoneNumber();
        this.code = sms.getCode();
    }
}
