package com.ssafy.a107.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }
}
