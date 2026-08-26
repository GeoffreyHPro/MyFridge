package com.example.demo.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.meal.MealLightDto;
import com.example.demo.model.Meal;
import com.example.demo.model.MealItem;
import com.example.demo.repository.MealItemRepository;
import com.example.demo.repository.MealRepository;

@Service
public class MealService {
    
    private MealRepository mealRepository;

    private MealItemRepository mealItemRepository;

    public MealService(
        MealRepository mealRepository,
        MealItemRepository mealItemRepository
    ){
        this.mealRepository = mealRepository;
        this.mealItemRepository = mealItemRepository;
    }

    public Meal getMealById(String id) throws NotFoundException{
        return mealRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public MealLightDto getMealLightDto(String id) throws NotFoundException{
        Meal meal = getMealById(id);
        List<MealItem> mealItems = mealItemRepository.findByProductId(id);
        return new MealLightDto();
    }
}
