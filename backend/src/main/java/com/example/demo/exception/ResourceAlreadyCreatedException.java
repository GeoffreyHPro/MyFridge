package com.example.demo.exception;

public class ResourceAlreadyCreatedException extends RuntimeException {
    public ResourceAlreadyCreatedException(String message) {
        super(message);
    }
}
