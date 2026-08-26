package com.example.demo.model;

import java.io.IOException;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    public enum Status {
        ACTIVE, DELETED
    };

    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(unique = true)
    private String ean;

    private String name;

    private String detail;

    private String status;

    @Column(columnDefinition = "bytea")
    private byte[] image;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Nutrition nutrition;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    public Product(String ean, String name, String detail) {
        this.ean = ean;
        this.name = name;
        this.detail = detail;

        try {
            this.image = getClass().getClassLoader().getResourceAsStream("static/default.png")
                    .readAllBytes();
        } catch (IOException e) {
        }
    }


}
