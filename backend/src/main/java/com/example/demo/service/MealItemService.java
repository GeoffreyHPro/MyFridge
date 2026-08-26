package com.example.demo.service;

import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.demo.command.MealItemCommand;
import com.example.demo.command.MealItemQuantityCommand;
import com.example.demo.model.MealItem;
import com.example.demo.model.Product;
import com.example.demo.model.Meal;
import com.example.demo.repository.MealItemRepository;
import com.example.demo.repository.MealRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class MealItemService {
    
    private MealRepository mealRepository;
    private MealItemRepository mealItemRepository;
    private ProductRepository productRepository;

    public MealItemService(
        MealItemRepository mealItemRepository,
        MealRepository mealRepository,
        ProductRepository productRepository
    ){
        this.mealRepository = mealRepository;
        this.mealItemRepository = mealItemRepository;
        this.productRepository = productRepository;
    }

    public void deleteMealitem(String id){
        MealItem mealItem = mealItemRepository.findById(id).get();
        mealItemRepository.delete(mealItem);
    }

    public void createMealItem(String id, MealItemCommand mealItemCommand){
        Meal meal = mealRepository.findById(id).get();
        Product product = productRepository.findById(mealItemCommand.productId()).get();

        MealItem mealItem = new MealItem(meal, product, mealItemCommand.quantity()); 
        mealItemRepository.save(mealItem);
    }
    public void updateMealItem(String id, String mealItemId, MealItemQuantityCommand mealItemQuantityCommand) throws NameNotFoundException{
        Optional<Meal> meal = mealRepository.findById(id);

        if(!meal.isPresent()){
            throw new NameNotFoundException();
        }

        Optional<MealItem> mealItem = meal.get().getMealItems().stream()
            .filter(item -> item.getId().equals(mealItemId)).findFirst();

        if(!mealItem.isPresent()){
            throw new NameNotFoundException();
        }

        mealItem.get().setQuantity(mealItemQuantityCommand.quantity());;
        mealItemRepository.save(mealItem.get());
    }
}
