package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRichDto {
    private String id;
    private String ean;
    private String name;
    private String detail;

    public ProductRichDto() {
    }

    public ProductRichDto(String id, String ean, String name, String detail) {
        this.id = id;
        this.ean = ean;
        this.name = name;
        this.detail = detail;
    }
}
