package com.example.demo.exception.product;

import com.example.demo.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
