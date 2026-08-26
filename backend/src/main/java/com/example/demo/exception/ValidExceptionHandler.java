package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidExceptionHandler {
    
    /*@ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                Map.of(
                                                "message", ex.getMessage()));
        }*/
}
