package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsNutriments(
        @JsonProperty("energy-kcal_100g") Double calories,
        @JsonProperty("proteins_100g") Double proteins,
        @JsonProperty("carbohydrates_100g") Double carbohydrates,
        @JsonProperty("fat_100g") Double lipids
) {}