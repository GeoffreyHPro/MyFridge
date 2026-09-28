package com.example.demo.dto.nutritionDay;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;

public record NutritionDayLightDto(
    String id,
    @Nullable LocalDate date
) {}
