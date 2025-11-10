package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @NotNull
    private String id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private String type;
    private String status;

    public Log() {
    }

    public Log(String status, String type) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }
}
