package com.picsart.intership.exaption;

import com.picsart.intership.utils.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHendler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.warn(ex.getMessage());
        return new ResponseEntity<>(new MessageResponse("Have error"), HttpStatus.BAD_REQUEST);
    }
}
