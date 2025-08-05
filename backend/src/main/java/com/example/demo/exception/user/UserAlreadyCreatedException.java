package com.example.demo.exception.user;

import com.example.demo.exception.ResourceAlreadyCreatedException;

public class UserAlreadyCreatedException extends ResourceAlreadyCreatedException {
    public UserAlreadyCreatedException() {
        super("User already created");
    }
}