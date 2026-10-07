package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.openproductsfacts.OpenProductsFactsSearchResponse;
import com.example.demo.service.OpenProductsFactsImportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/import/products")
public class ProductImportController {
    private final OpenProductsFactsImportService importService;

    public ProductImportController(OpenProductsFactsImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/open-products-facts")
    @SecurityRequirement(name = "Authorization")
    
    public ResponseEntity<OpenProductsFactsSearchResponse> importProducts(
            @RequestParam(defaultValue = "1") int startPage,
            @RequestParam(defaultValue = "100") int pageSize,
            @RequestParam(defaultValue = "10") int maxPages) {

                return ResponseEntity.ok().body(importService.importPage(
                Math.max(1, startPage),
                Math.min(1000, Math.max(1, pageSize)),
                Math.min(100, Math.max(1, maxPages))));
    }
}