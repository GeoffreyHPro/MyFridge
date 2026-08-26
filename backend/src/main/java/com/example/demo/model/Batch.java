package com.example.demo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batches")
@Getter
@NoArgsConstructor
public class Batch {

    @NotNull
    @Id
    private String id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private int quantity;

    private LocalDate expirationDate;
    
    private String status;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.expirationDate = LocalDate.now();
    }

    public Batch(User user, Product product, int quantity) {
        this.product = product;
        this.user = user;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
