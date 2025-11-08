package com.example.demo.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record PageableCommand(
                @NotNull @JsonProperty("page") String page,
                @NotNull @JsonProperty("size") String size) {

}
