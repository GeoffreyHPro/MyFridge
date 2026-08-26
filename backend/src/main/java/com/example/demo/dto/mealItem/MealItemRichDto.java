package com.example.demo.dto.mealItem;

import jakarta.validation.constraints.NotNull;

public record MealItemRichDto(
        @NotNull String id,
        @NotNull String name,
        @NotNull Float quantity,
        @NotNull Float calories,
        @NotNull Float lipids,
        @NotNull Float proteins,
        @NotNull Float carbohydrates) {
}
