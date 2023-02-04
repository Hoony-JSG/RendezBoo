package com.ssafy.a107.api.handler;

import com.ssafy.a107.common.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
@Slf4j
public class ExceptionController {
//    @org.springframework.web.bind.annotation.ExceptionHandler({
//            //김명준 팀원이 추가 예정
//    })

    //400 BAD REQUEST
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequestException(final BadRequestException ex){
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    //401 UNAUTHORIZED
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 404 NOT FOUND
    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 409 CONFLICT
    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<?> handleConflictException(final ConflictException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Sms
    @ExceptionHandler({SmsException.class})
    public ResponseEntity<?> handleSmsException(final SmsException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // Token
    @ExceptionHandler({TokenException.class})
    public ResponseEntity<?> handleTokenException(final TokenException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // JwtInvalidException
    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<?> handleJwtInvalidException(final JwtInvalidException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // 500 INTERNAL_SERVER_ERROR
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }


}
