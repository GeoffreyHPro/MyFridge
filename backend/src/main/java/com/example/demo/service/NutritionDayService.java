package com.example.demo.service;

import com.example.demo.repository.NutritionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.converter.NutritionDayConverter;
import com.example.demo.dto.nutritionDay.NutritionDayLightDto;
import com.example.demo.dto.nutritionDay.NutritionDayRichDto;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.model.NutritionDay;
import com.example.demo.model.User;
import com.example.demo.repository.NutritionDayRepository;

@Service
public class NutritionDayService {

    private NutritionRepository nutritionRepository;

    private NutritionDayRepository nutritionDayRepository;

    private NutritionDayConverter nutritionDayConverter;

    public NutritionDayService(
            NutritionDayRepository nutritionDayRepository,
            NutritionRepository nutritionRepository,
            NutritionDayConverter nutritionDayConverter
    ) {
        this.nutritionDayRepository = nutritionDayRepository;
        this.nutritionRepository = nutritionRepository;
        this.nutritionDayConverter = nutritionDayConverter;
    }

    public void createNutritionDay(User user) {
        NutritionDay nutritionDay = new NutritionDay(user);
        nutritionDayRepository.save(nutritionDay);
    }

    public List<NutritionDayLightDto> getNutritionDaysByUser(User user) {
        List<NutritionDay> nutritionDays = nutritionDayRepository.findNutritionDaysByUser(user);
        
        return nutritionDays.stream().map(nutritionDay -> nutritionDayConverter.applyLight(nutritionDay)).toList();
    }

    public NutritionDayRichDto getNutritionDayByUser(User user, String id) {
        Optional<NutritionDay> nutritionDay = nutritionDayRepository.findNutritionDayByUserAndId(user, id);

        if (!nutritionDay.isPresent()) {
            throw new UserNotFoundException();
        }

        return nutritionDayConverter.applyRich(nutritionDay.get());
    }

}
