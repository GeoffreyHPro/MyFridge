package com.example.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.example.demo.dto.openproductsfacts.OpenProductsFactsSearchResponse;

@Service
public class OpenProductsFactsClient {
    private final RestClient client;

    public OpenProductsFactsClient(
            RestClient.Builder builder,
            @Value("${openproductsfacts.base-url:https://world.openproductsfacts.org}")
            String baseUrl) {
        this.client = builder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.USER_AGENT, "MyFridge/1.0")
                .build();
    }

    public OpenProductsFactsSearchResponse getPage(int page, int pageSize) {
        return client.get()
                .uri(uri -> uri.path("/api/v2/search")
                        .queryParam("fields",
                                "code,product_name,brands,quantity,nutriments")
                        .queryParam("page", page)
                        .queryParam("page_size", pageSize)
                        .build())
                .retrieve()
                .body(OpenProductsFactsSearchResponse.class);
    }
}