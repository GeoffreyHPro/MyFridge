package com.example.demo.dto.meal;

import java.util.List;

import com.example.demo.dto.mealItem.MealItemRichDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealRichDto {

    private String id;
    
    private Float calories;

    private Float lipids;

    private Float proteins;

    private Float carbohydrates;

    private List<MealItemRichDto> mealItems;
}
