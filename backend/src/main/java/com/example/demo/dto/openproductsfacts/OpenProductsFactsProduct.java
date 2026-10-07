package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsProduct(
        String code,
        @JsonProperty("product_name") String productName,
        String brands,
        OpenProductsFactsNutrition nutrition) {}