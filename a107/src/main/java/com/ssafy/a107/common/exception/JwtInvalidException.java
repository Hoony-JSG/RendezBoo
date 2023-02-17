package com.ssafy.a107.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtInvalidException extends Exception {

    public JwtInvalidException(String message) {
        super(message);
    }

    public JwtInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
