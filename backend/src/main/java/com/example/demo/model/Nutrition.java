package com.example.demo.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "nutritions")
@Getter
@Setter
@NoArgsConstructor
public class Nutrition {

    @NotNull
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    private Float calories;

    private Float proteins;

    private Float lipids;

    private Float carbohydrates;

    private String nutritionBasis;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    public Nutrition(Float calories, Float proteins, Float lipids, Float carbohydrates, String nutritionBasis) {
        this.calories = calories;
        this.proteins = proteins;
        this.lipids = lipids;
        this.carbohydrates = carbohydrates;
        this.nutritionBasis = nutritionBasis;
    }

}
