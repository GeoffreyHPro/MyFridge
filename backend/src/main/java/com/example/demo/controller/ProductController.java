package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.ProductCommand;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.ProductLightDto;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/product")
@Api(tags = "Product", description = "Endpoint")
public class ProductController {

    private ProductService productService;
    private ProductConverter productConverter;

    ProductController(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{ean}")
    public ResponseEntity<ProductLightDto> getProduct(@PathVariable String ean) {
        Product product = productService.getProduct(ean);
        return ResponseEntity.status(HttpStatus.OK).body(productConverter.applyLight(product));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<Page<Product>> getProducts(@PathParam(value = "page") int page,
            @PathParam(value = "size") int size, @PathParam(value = "name") String name) {
        Page<Product> products = productService.getProducts(page, size, name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PostMapping()
    public ResponseEntity<Object> createProduct(@RequestBody @Valid ProductCommand productCommand) {
        productService.addProduct(productConverter.createProduct(productCommand));
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

}
