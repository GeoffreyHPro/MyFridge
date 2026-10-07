package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsAggregatedNutrients(
        OpenProductsFactsNutrientDetail proteins,
        @JsonProperty("fat") OpenProductsFactsNutrientDetail fat,
        @JsonProperty("carbohydrates") OpenProductsFactsNutrientDetail carbohydrates,
        @JsonProperty("energy-kcal") OpenProductsFactsNutrientDetail energyKcal) {}
