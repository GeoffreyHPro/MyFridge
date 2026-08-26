package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record MealItemQuantityCommand(
    @NotNull @JsonProperty("quantity") Float quantity) {
}
