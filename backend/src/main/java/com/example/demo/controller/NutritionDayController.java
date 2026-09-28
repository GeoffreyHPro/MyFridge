package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.nutritionDay.NutritionDayLightDto;
import com.example.demo.dto.nutritionDay.NutritionDayRichDto;
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
            NutritionDayService nutritionDayService
    ) {
        this.userService = userService;
        this.nutritionDayService = nutritionDayService;
    }

    /* ----------- GET Endpoints --------- */

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getNutritionDay(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
                nutritionDayService.getNutritionDayByUser(user, id));
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<NutritionDayLightDto>> getNutritionDays() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
                nutritionDayService.getNutritionDaysByUser(user));
    }

    /**
     * Resource return nutritionDay linked to date given or null object
     * 
     * @param date LocalDate : date for nutritionDay
     * @return single NutritionDayRichDto or null object
     */
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/date/{date}")
    public ResponseEntity<NutritionDayRichDto> getNutritionDaysByDate(
            @PathVariable LocalDate date) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());

        return ResponseEntity.ok(
                nutritionDayService.getNutritionDaysByUserAndDate(user, date));
    }

    /* -------------- POST Endpoints --------- */

    /**
     * Resource to create new nutrition day and return nutrition day
     * 
     * @param date LocalDate : date to linked with new nutrition day
     * @return
     */
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @PostMapping()
    public ResponseEntity<NutritionDayRichDto> createNutritionDay(@RequestBody CreateNutritionDayRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByPseudo(auth.getName());
        NutritionDayRichDto nutritionDayRichDto = nutritionDayService.createNutritionDay(user, request.date());
        return ResponseEntity.status(HttpStatus.CREATED).body(nutritionDayRichDto);
    }

    public record CreateNutritionDayRequest(LocalDate date) {
    }

}
