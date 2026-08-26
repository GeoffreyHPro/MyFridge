package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record NutritionCommand(
    @NotNull @JsonProperty("calories") Float calories, 
    @NotNull @JsonProperty("proteins") Float proteins,
    @NotNull @JsonProperty("lipids") Float lipids,
    @NotNull @JsonProperty("carbohydrates") Float carbohydrates
) {}
