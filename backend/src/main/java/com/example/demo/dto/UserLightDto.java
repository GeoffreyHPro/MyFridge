package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLightDto {
    private String pseudo;

    public UserLightDto() {
    }

    public UserLightDto(String pseudo) {
        this.pseudo = pseudo;
    }
}
