package com.example.demo.factory;

import org.springframework.stereotype.Service;

import com.example.demo.command.ProductCommand;
import com.example.demo.model.Nutrition;
import com.example.demo.model.Product;

@Service
public class ProductFactory {

    public Product apply(Product product, ProductCommand productCommand) {
        Nutrition nutrition = product.getNutrition();

        product.setName(productCommand.name());
        product.setDetail(productCommand.detail());
        product.setEan(productCommand.ean());
        nutrition.setCalories(productCommand.calories());
        nutrition.setProteins(productCommand.proteins());
        nutrition.setCarbohydrates(productCommand.carboHydrates());
        nutrition.setLipids(productCommand.lipids());

        return product;
    }
}
