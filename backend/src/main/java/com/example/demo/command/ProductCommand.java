package com.example.demo.command;

import com.example.demo.command.validator.Ean;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCommand(
                @NotNull @Ean @JsonProperty("ean") String ean,
                @NotNull @Size(min = 3, max = 10) @JsonProperty("name") String name,
                @NotNull @Size(min = 3, max = 30) @JsonProperty("detail") String detail,
                @NotNull @JsonProperty("status") String status) {
}
