package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record ProductCommand(
        @NotNull @JsonProperty("ean") String ean,
        @NotNull @JsonProperty("name") String name,
        @JsonProperty("detail") String detail,
        @JsonProperty("status") String status) {
}
