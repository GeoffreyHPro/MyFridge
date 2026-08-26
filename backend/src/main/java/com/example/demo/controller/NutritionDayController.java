package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.nutritionDay.NutritionDayLightDto;
import com.example.demo.model.User;
import com.example.demo.service.NutritionDayService;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/nutritionDay")
@SecurityRequirement(name = "Authorization")
@Api(tags = "NutritionDay")
public class NutritionDayController {

    private UserService userService;

    private NutritionDayService nutritionDayService;

    public NutritionDayController(
        UserService userService,
        NutritionDayService nutritionDayService)
    {
        this.userService = userService;
        this.nutritionDayService = nutritionDayService;
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getNutritionDay(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
            nutritionDayService.getNutritionDayByUser(user, id)
        );
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<NutritionDayLightDto>> getNutritionDays() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
            nutritionDayService.getNutritionDaysByUser(user)
        );
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @PostMapping()
    public ResponseEntity<List<NutritionDayLightDto>> createNutritionDay() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        nutritionDayService.createNutritionDay(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            nutritionDayService.getNutritionDaysByUser(user)
        );
    }
}
