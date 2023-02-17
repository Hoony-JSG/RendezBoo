package com.ssafy.a107.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConflictException extends Exception {

    public ConflictException(String message) {
        super(message);
    }
}
