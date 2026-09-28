package com.example.demo.model;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nutrition_days")
@Getter
@NoArgsConstructor
public class NutritionDay {

    @Id
    @NotNull
    private String id;

    @NotNull
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal breakfast;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal morningSnack;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal lunch;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal afternoonSnack;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal dinner;

    @ManyToOne
    private User user;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    public NutritionDay(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        this.breakfast = new Meal();
        this.morningSnack = new Meal();
        this.lunch = new Meal();
        this.afternoonSnack = new Meal();
        this.dinner = new Meal();
    }

    public Float getCalories() {
        return sumMeals(Meal::getCalories);
    }

    public Float getProteins() {
        return sumMeals(Meal::getProteins);
    }

    public Float getCarbohydrates() {
        return sumMeals(Meal::getCarbohydrates);
    }

    public Float getLipids() {
        return sumMeals(Meal::getLipids);
    }

    private float sumMeals(Function<Meal, Float> extractor) {
        return valueOf(breakfast, extractor)
                + valueOf(morningSnack, extractor)
                + valueOf(lunch, extractor)
                + valueOf(afternoonSnack, extractor)
                + valueOf(dinner, extractor);
    }

    private float valueOf(Meal meal, Function<Meal, Float> extractor) {
        return meal == null ? 0f : extractor.apply(meal);
    }
}
