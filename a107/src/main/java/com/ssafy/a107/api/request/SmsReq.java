package com.ssafy.a107.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsReq {

    private String phoneNumber;
    private String code;

    public void parsePhoneNumber() {
        this.phoneNumber.replaceAll("-", "");
    }
}
