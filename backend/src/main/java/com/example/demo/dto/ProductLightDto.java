package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductLightDto {
    private String ean;

    public ProductLightDto() {
    }

    public ProductLightDto(String ean) {
        this.ean = ean;
    }
}
