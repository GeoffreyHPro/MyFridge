package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.dto.meal.MealLightDto;
import com.example.demo.dto.meal.MealRichDto;
import com.example.demo.model.Meal;

@Service
public class MealConverter {

    private MealItemConverter mealItemConverter;

    public MealConverter(MealItemConverter mealItemConverter) {
        this.mealItemConverter = mealItemConverter;
    }

    public MealLightDto applyLight(Meal meal) {
        MealLightDto mealLightDto = new MealLightDto(
                meal.getCalories(),
                meal.getLipids(),
                meal.getProteins(),
                meal.getCarbohydrates());

        return mealLightDto;
    }

    public MealRichDto applyRich(Meal meal) {
        MealRichDto mealRichDto = new MealRichDto(
                meal.getId(),
                meal.getCalories(),
                meal.getLipids(),
                meal.getProteins(),
                meal.getCarbohydrates(),
                meal.getMealItems().stream()
                        .map(mealItem -> mealItemConverter.applyRich(mealItem)).toList());

        return mealRichDto;
    }
}
