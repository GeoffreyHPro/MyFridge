package com.example.demo.dto.openproductsfacts;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenProductsFactsSearchResponse(
        List<OpenProductsFactsProduct> products,
        @JsonProperty("page_count") Integer pageCount) {}