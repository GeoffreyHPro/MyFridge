package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.dto.mealItem.MealItemRichDto;
import com.example.demo.model.MealItem;

@Service
public class MealItemConverter {
    
    public MealItemRichDto applyRich(MealItem mealItem){
        MealItemRichDto mealItemRichDto = new MealItemRichDto(
            mealItem.getId(),
            mealItem.getName(),
            mealItem.getQuantity(),
            mealItem.getCalories(),
            mealItem.getLipids(),
            mealItem.getProteins(),
            mealItem.getCarbohydrates()
        );

        return mealItemRichDto;
    }
}
