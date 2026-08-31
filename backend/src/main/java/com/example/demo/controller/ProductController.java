package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.NutritionCommand;
import com.example.demo.command.ProductCommand;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.product.ProductLightDto;
import com.example.demo.dto.product.ProductRichDto;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/product")
@Api(tags = "Product")
public class ProductController {

    private ProductService productService;
    private ProductConverter productConverter;

    ProductController(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    /* -------------------- GET endpoints -------------------------------- */

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductRichDto> getProduct(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productConverter.applyRich(product));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<Page<ProductLightDto>> getProducts(@PathParam(value = "page") int page,
            @PathParam(value = "size") int size, @RequestParam(required = false) String name) {
        Page<Product> products = productService.getProducts(page, size, name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(products.map((Product product) -> productConverter.applyLight(product)));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<List<ProductLightDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(products.stream().map(product -> productConverter.applyLight(product)).toList());
    }

    /* -------------------- POST endpoints -------------------------------- */

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PostMapping()
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductCommand productCommand) {
        productService.addProduct(productCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    /* -------------------- PUT endpoints -------------------------------- */

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id,
            @Valid @RequestBody ProductCommand productCommand) {
        Product product = productService.updateProduct(id, productCommand);
        return ResponseEntity.status(HttpStatus.OK).body(productConverter.applyRich(product));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PutMapping("/{id}/nutrition")
    public ResponseEntity<Object> updateProduct(@PathVariable String id,
            @Valid @RequestBody NutritionCommand nutritionCommand) {
        Product product = productService.updateNutrition(id, nutritionCommand);
        return ResponseEntity.status(HttpStatus.OK).body(productConverter.applyRich(product));
    }

    /* -------------------- DELETE endpoints -------------------------------- */

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
