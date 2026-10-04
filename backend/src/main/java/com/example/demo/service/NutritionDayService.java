package com.example.demo.service;

import java.time.LocalDate;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NutritionDayService {

    private NutritionDayRepository nutritionDayRepository;

    private NutritionDayConverter nutritionDayConverter;

    public NutritionDayService(
            NutritionDayRepository nutritionDayRepository,
            NutritionDayConverter nutritionDayConverter) {
        this.nutritionDayRepository = nutritionDayRepository;
        this.nutritionDayConverter = nutritionDayConverter;
    }

    /* ------------------- GET --------------------------- */

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

    public NutritionDayRichDto getNutritionDaysByUserAndDate(User user, LocalDate date) {
        Optional<NutritionDay> nutritionDay = nutritionDayRepository.findNutritionDaysByUserAndDate(user, date);

        if (!nutritionDay.isPresent()) {
            log.info("Nutrition day not found for userId = {} and date = {}", user.getId(), date);
            return null;
        }

        log.debug("Nutrition day found for userId={} and date={}", user.getId(), date);
        return nutritionDayConverter.applyRich(nutritionDay.get());
    }

    /* ------------------- Create --------------------------- */

    /**
     * Function to create new daily nutrition
     * 
     * @param user User : current user
     * @param date LocalDate : date of daily nutrition
     * @return daily nutrition from user in the date given
     */
    public NutritionDayRichDto createNutritionDay(User user, LocalDate date) {
        NutritionDay nutritionDay = new NutritionDay(user, date);
        nutritionDayRepository.save(nutritionDay);

        log.info("Nutrition day created for userId={} and date={}", user.getId(), date);

        return nutritionDayConverter.applyRich(nutritionDay);
    }

}
