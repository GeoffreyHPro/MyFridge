package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.exception.product.ProductAlreadyCreatedException;
import com.example.demo.exception.product.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

  private ProductRepository productRepository;

  ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product getProduct(String ean) {
    return productRepository.findByEan(ean).orElseThrow(() -> new ProductNotFoundException());
  }

  public Page<Product> getProducts(int page, int size, String name) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
    return productRepository.getProducts(name, pageable);
  }

  public Product addProduct(Product product) {
    Optional<Product> productFound = this.productRepository.findByEan(product.getEan());

    if (productFound.isPresent()) {
      throw new ProductAlreadyCreatedException();
    }

    productRepository.save(product);
    return product;
  }

}
