package com.example.demo.dto.mealItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealItemLightDto {
    
    private String id;

    private String mealId;

    private String productId;

    private Float quantity;
}
