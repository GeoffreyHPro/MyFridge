package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.dto.nutritionDay.NutritionDayLightDto;
import com.example.demo.dto.nutritionDay.NutritionDayRichDto;
import com.example.demo.model.NutritionDay;

@Service
public class NutritionDayConverter {

    private MealConverter mealConverter;

    public NutritionDayConverter(MealConverter mealConverter){
        this.mealConverter = mealConverter;
    }

    public NutritionDayLightDto applyLight(NutritionDay nutritionDay){
        NutritionDayLightDto nutritionDayLightDto = new NutritionDayLightDto(
            nutritionDay.getId()
        );

        return nutritionDayLightDto;
    }

    public NutritionDayRichDto applyRich(NutritionDay nutritionDay){
        NutritionDayRichDto nutritionDayRichDto = new NutritionDayRichDto(
            nutritionDay.getId(),
            nutritionDay.getBreakfast() == null ? null : mealConverter.applyRich(nutritionDay.getBreakfast()),
            nutritionDay.getMorningSnack() == null ? null : mealConverter.applyRich(nutritionDay.getMorningSnack()),
            nutritionDay.getLunch() == null ? null : mealConverter.applyRich(nutritionDay.getLunch()),
            nutritionDay.getAfternoonSnack() == null ? null : mealConverter.applyRich(nutritionDay.getAfternoonSnack()),
            nutritionDay.getDinner() == null ? null : mealConverter.applyRich(nutritionDay.getDinner()),
            nutritionDay.getCalories(),
            nutritionDay.getProteins(),
            nutritionDay.getCarbohydrates(),
            nutritionDay.getLipids()
        );

        return nutritionDayRichDto;
    }
}
