package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsNutrientDetail(
        Double value,
        String unit
) {}
