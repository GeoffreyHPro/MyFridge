package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.image.ImageNotFoundException;

@Service
public class ImageService {

    private final ProductService productService;

    public ImageService(ProductService productService) {
        this.productService = productService;
    }

    public byte[] getImage(String id) {
        return productService.getProductById(id).getImage();
    }

    public void saveFile(String id, MultipartFile file) {
        try {
            productService.updateImage(id, file);
        } catch (Exception e) {
            throw new ImageNotFoundException();
        }
    }
}
