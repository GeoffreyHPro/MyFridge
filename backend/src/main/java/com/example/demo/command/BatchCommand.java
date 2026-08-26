package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record BatchCommand(
                @NotNull @JsonProperty("productId") String productId,
                @NotNull @JsonProperty("quantity") int quantity) {
}