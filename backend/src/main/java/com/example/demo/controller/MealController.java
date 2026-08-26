package com.example.demo.controller;

import javax.naming.NameNotFoundException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.MealItemCommand;
import com.example.demo.command.MealItemQuantityCommand;
import com.example.demo.converter.MealConverter;
import com.example.demo.model.Meal;
import com.example.demo.service.MealItemService;
import com.example.demo.service.MealService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/meal")
@SecurityRequirement(name = "Authorization")
@Api(tags = "Meal")
public class MealController {
    
    private MealService mealService;

    private MealConverter mealConverter;

    private MealItemService mealItemService;

    public MealController(
        MealService mealService, 
        MealConverter mealConverter,
        MealItemService mealItemService
    ){
        this.mealService = mealService;
        this.mealConverter = mealConverter;
        this.mealItemService = mealItemService;
    }

    /*@SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<MealLightDto> getProduct(@PathVariable String id) throws NotFoundException {
        MealLightDto mealLightDto = mealService.getMealLightDto(id);
        return mealLightDto;
    }*/

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeal(@PathVariable String id) throws NotFoundException {
        Meal meal = mealService.getMealById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mealConverter.applyLight(meal));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}/full")
    public ResponseEntity<?> getMealFull(@PathVariable String id) throws NotFoundException {
        Meal meal = mealService.getMealById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mealConverter.applyRich(meal));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @PostMapping("/{id}/mealItem")
    public ResponseEntity<?> addMeal(@PathVariable String id, @RequestBody MealItemCommand mealItemCommand) throws NotFoundException {
        mealItemService.createMealItem(id, mealItemCommand);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @PatchMapping("/{id}/mealItem/{mealItemId}")
    public ResponseEntity<?> updateMeal(@PathVariable String id, @PathVariable String mealItemId, @RequestBody MealItemQuantityCommand mealItemQuantityCommand) throws NotFoundException, NameNotFoundException {
        mealItemService.updateMealItem(id, mealItemId, mealItemQuantityCommand);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @DeleteMapping("/{id}/mealItem/{mealItemId}")
    public ResponseEntity<?> deleteMealitem(@PathVariable String id, @PathVariable String mealItemId) throws NotFoundException, NameNotFoundException {
        mealItemService.deleteMealitem(mealItemId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
