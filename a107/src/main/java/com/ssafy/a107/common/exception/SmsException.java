package com.ssafy.a107.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SmsException extends Exception {

    public SmsException(String message) {
        super(message);
    }
}
