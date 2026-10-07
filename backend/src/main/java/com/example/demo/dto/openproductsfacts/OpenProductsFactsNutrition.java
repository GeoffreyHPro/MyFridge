package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsNutrition(
        OpenProductsFactsAggregatedEntry aggregated_set) {}
