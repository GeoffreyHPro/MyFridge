package com.example.demo.dto.openproductsfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsAggregatedEntry(
        @JsonProperty("nutrients") OpenProductsFactsAggregatedNutrients nutrients,
        String per) {}
