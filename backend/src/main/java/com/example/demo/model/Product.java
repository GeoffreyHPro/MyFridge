package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    public enum Status {
        ACTIVE, DELETED
    };

    @Id
    @NotNull
    private String id;
    @Column(unique = true)
    private String ean;
    private String name;
    private String detail;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, updatable = false)
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String status;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public Product() {
    }

    public Product(String ean,
            String name, String detail) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
