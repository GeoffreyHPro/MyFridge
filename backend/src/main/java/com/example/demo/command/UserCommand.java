package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record UserCommand(
        @NotNull @JsonProperty("pseudo") String pseudo,
        @NotNull @JsonProperty("password") String password) {
}
