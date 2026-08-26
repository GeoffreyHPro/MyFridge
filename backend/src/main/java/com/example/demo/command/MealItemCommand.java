package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record MealItemCommand(
    @NotNull @JsonProperty("productId") String productId,
    @NotNull @JsonProperty("quantity") Float quantity
) {}
