package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.command.ProductCommand;
import com.example.demo.dto.product.ProductLightDto;
import com.example.demo.dto.product.ProductRichDto;
import com.example.demo.model.Product;

@Service
public class ProductConverter {

    public Product createProduct(ProductCommand productCommand) {
        Product productCreated = new Product(
            productCommand.ean(),
            productCommand.name(),
            productCommand.detail()
        );

        return productCreated;
    }

    public ProductLightDto applyLight(Product product) {
        ProductLightDto productLightDto = new ProductLightDto(
            product.getId(),
            product.getEan(),
            product.getName()
        );

        return productLightDto;
    }

    public ProductRichDto applyRich(Product product) {
        ProductRichDto productRichDto = new ProductRichDto(
            product.getId(),
            product.getEan(), 
            product.getName(),
            product.getDetail(),
            product.getNutrition().getCalories(),
            product.getNutrition().getProteins(),
            product.getNutrition().getCarbohydrates(),
            product.getNutrition().getLipids()
        );

        return productRichDto;
    }
}
