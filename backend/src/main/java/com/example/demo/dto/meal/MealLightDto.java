package com.example.demo.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealLightDto {

    private Float calories;

    private Float lipids;

    private Float proteins;

    private Float carbohydrates;
}
