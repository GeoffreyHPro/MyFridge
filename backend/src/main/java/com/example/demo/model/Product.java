package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @NotNull
    private String id;
    private String ean;
    private String name;
    private String detail;

    public Product() {
    }

    public Product(String ean,
            String name, String detail) {
        this.id = UUID.randomUUID().toString();
        this.ean = ean;
        this.name = name;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public String getEan() {
        return ean;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
