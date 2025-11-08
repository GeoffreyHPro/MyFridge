package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.command.ProductCommand;
import com.example.demo.dto.ProductLightDto;
import com.example.demo.dto.ProductRichDto;
import com.example.demo.model.Product;

@Service
public class ProductConverter {

    public Product createProduct(ProductCommand productCommand) {
        Product productCreated = new Product(productCommand.ean(), productCommand.name(),
                productCommand.detail());
        return productCreated;
    }

    public ProductLightDto applyLight(Product product) {
        ProductLightDto productLightDto = new ProductLightDto(product.getEan());
        return productLightDto;
    }

    public ProductRichDto applyRich(Product product) {
        ProductRichDto productrRichDto = new ProductRichDto(product.getId(), product.getEan(), product.getName(),
                product.getDetail());
        return productrRichDto;
    }
}
