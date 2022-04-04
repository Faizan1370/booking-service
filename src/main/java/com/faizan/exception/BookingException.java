package com.faizan.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

//@ControllerAdvice
@RestControllerAdvice
public class BookingException {

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<?> handlBadRequestException(BadRequestException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }

    // return the spring bean validation error which is like @notNUll,@NOTBLANK in DTo or Entity
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String, String> methodArgument(MethodArgumentNotValidException ex) {
        final Map<String, String> errorMap = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());

        });
        return errorMap;

    }

}
