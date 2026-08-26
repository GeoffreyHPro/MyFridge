package com.example.demo.command;

import com.example.demo.command.validator.Ean;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductCommand(
        @Nullable @Ean @JsonProperty("ean") String ean,
        @NotNull @Size(min = 3, max = 10) @JsonProperty("name") String name,
        @Nullable @Size(min = 3, max = 30) @JsonProperty("detail") String detail,
        @NotNull @Positive @JsonProperty("calories") Float calories,
        @NotNull @Positive @JsonProperty("proteins") Float proteins,
        @NotNull @Positive @JsonProperty("carboHydrates") Float carboHydrates,
        @NotNull @Positive @JsonProperty("lipids") Float lipids
) {}
