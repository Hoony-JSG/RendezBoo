package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SMSRes {

    private String phoneNumber;
    private String code;
}
