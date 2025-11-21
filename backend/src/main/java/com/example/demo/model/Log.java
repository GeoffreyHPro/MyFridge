package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "logs")
public class Log {

    public enum LogType {
        CREATE, READ, UPDATE, DELETE
    };

    @Id
    @NotNull
    private String id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private LogType type;
    @Column(nullable = false, updatable = false)
    private String status;
    private String objectId;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public Log() {
    }

    public Log(String status, LogType type, String createdBy, String objectId) {
        this.createdBy = createdBy;
        this.type = type;
        this.status = status;
        this.objectId = objectId;
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

    public LogType getTypeString() {
        return type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(LogType type) {
        this.type = type;
    }

}
