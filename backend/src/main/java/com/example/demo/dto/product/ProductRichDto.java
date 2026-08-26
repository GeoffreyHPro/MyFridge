package com.example.demo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRichDto {
    private String id;
    private String ean;
    private String name;
    private String detail;
    private Float calories;
    private Float proteins;
    private Float carbohydrates;
    private Float lipids;

}
