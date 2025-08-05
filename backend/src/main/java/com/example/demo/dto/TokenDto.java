package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String jwt;
    private String role;

    public TokenDto() {
    }

    public TokenDto(String jwt, String role) {
        this.jwt = jwt;
        this.role = role;
    }
}
