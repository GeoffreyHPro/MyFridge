package com.example.demo.exception.product;

import com.example.demo.exception.ResourceAlreadyCreatedException;

public class ProductAlreadyCreatedException extends ResourceAlreadyCreatedException {
    public ProductAlreadyCreatedException() {
        super("Product already created");
    }
}