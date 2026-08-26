package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.command.BatchCommand;
import com.example.demo.exception.product.ProductNotFoundException;
import com.example.demo.model.Batch;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.BatchRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class BatchService {
    private BatchRepository batchRepository;
    private ProductRepository productRepository;

    public BatchService(BatchRepository batchRepository, ProductRepository productRepository) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
    }

    public Batch addBatch(BatchCommand batchCommand, User user) {
        Optional<Product> productFound = this.productRepository.findById(batchCommand.productId());

        if (productFound.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Batch batch = new Batch(user, productFound.get(), batchCommand.quantity());
        batchRepository.save(batch);

        return batch;
    }
}
