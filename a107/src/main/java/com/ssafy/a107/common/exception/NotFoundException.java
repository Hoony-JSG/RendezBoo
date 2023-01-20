package com.ssafy.a107.common.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
