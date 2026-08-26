package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.command.NutritionCommand;
import com.example.demo.command.ProductCommand;
import com.example.demo.exception.product.ProductAlreadyCreatedException;
import com.example.demo.exception.product.ProductNotFoundException;
import com.example.demo.model.Nutrition;
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

  public Product getProductById(String id) {
    return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
  }

  public Page<Product> getProducts(int page, int size, String name) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
    return productRepository.getProducts(name, pageable);
  }

  public Product addProduct(Product product) {
    Optional<Product> productFound = this.productRepository.findByEan(product.getEan());

    if (productFound.isPresent() && productFound.get().getEan() != null) {
      throw new ProductAlreadyCreatedException();
    }

    Nutrition nutrition = new Nutrition(0F, 0F, 0F, 0F, "PER_100G");
    nutrition.setProduct(product);
    product.setNutrition(nutrition);
    productRepository.save(product);
    return product;
  }

  public Product addProduct(ProductCommand productCommand) {
    Optional<Product> productFound;
    if(productCommand.ean() != null ){
      productFound = this.productRepository.findByEan(productCommand.ean());
    }else{
      productFound = this.productRepository.findByName(productCommand.name());
    }

    if (productFound.isPresent()) {
      throw new ProductAlreadyCreatedException();
    }

    Product product = new Product(productCommand.ean(), productCommand.name(), productCommand.detail());

    Nutrition nutrition = new Nutrition(
      productCommand.calories(), 
      productCommand.proteins(),
      productCommand.lipids(),
      productCommand.carboHydrates(),
      "PER_100G"
    );

    nutrition.setProduct(product);
    product.setNutrition(nutrition);
    productRepository.save(product);
    return product;
  }

  public void updateImage(String id, MultipartFile file) throws Exception {
    Product product = getProductById(id);
    try {
      product.setImage(file.getBytes());
      productRepository.save(product);
    } catch (Exception e) {
      throw new Exception();
    }
  }

  public Product updateNutrition(String id, NutritionCommand nutritionCommand) {
    Product product = getProductById(id);
    product.getNutrition().setCalories(nutritionCommand.calories());
    product.getNutrition().setProteins(nutritionCommand.proteins());
    product.getNutrition().setLipids(nutritionCommand.lipids());
    product.getNutrition().setCarbohydrates(nutritionCommand.carbohydrates());

    productRepository.save(product);

    return product;
  }

  public void deleteProduct(String id){
    Optional<Product> product = productRepository.findById(id);

    if(!product.isPresent()){
      throw new ProductNotFoundException();
    }

    productRepository.delete(product.get());
  }

}
