package com.example.demo.dto.nutritionDay;

import com.example.demo.dto.meal.MealRichDto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record NutritionDayRichDto(
    @Nullable String id,
    @Nullable MealRichDto breakfast,
    @Nullable MealRichDto morningSnack,
    @Nullable MealRichDto lunch,
    @Nullable MealRichDto afternoonSnack,
    @Nullable MealRichDto dinner,
    @NotNull Float calories,
    @NotNull Float proteins,
    @NotNull Float carbohydrates,
    @NotNull Float lipids
) {}
