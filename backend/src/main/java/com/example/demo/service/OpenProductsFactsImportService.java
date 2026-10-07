package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.client.OpenProductsFactsClient;
import com.example.demo.dto.openproductsfacts.OpenProductsFactsProduct;
import com.example.demo.dto.openproductsfacts.OpenProductsFactsSearchResponse;
import com.example.demo.dto.product.ImportResult;
import com.example.demo.model.Nutrition;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class OpenProductsFactsImportService {
    private final OpenProductsFactsClient client;
    private final ProductRepository repository;

    public OpenProductsFactsImportService(
            OpenProductsFactsClient client,
            ProductRepository repository
    ) {
        this.client = client;
        this.repository = repository;
    }

    public OpenProductsFactsSearchResponse importPage(int startPage, int pageSize, int maxPages) {
       return client.getPage(1, pageSize);
    }


    /*public ImportResult importPages(int startPage, int pageSize, int maxPages) {
        int imported = 0;
        int skipped = 0;
        int pagesProcessed = 0;

        //for (int page = startPage; pagesProcessed < maxPages; page++) {
            OpenProductsFactsSearchResponse response =
                    client.getPage(1, pageSize);

            if (response == null || response.products() == null
                    || response.products().isEmpty()) {
                break;
            }

            List<OpenProductsFactsProduct> products = response.products();
            Set<String> eans = products.stream()
                    .map(OpenProductsFactsProduct::code)
                    .filter(code -> code != null && !code.isBlank())
                    .collect(java.util.stream.Collectors.toSet());

            Set<String> existing = new HashSet<>();
            repository.findAllByEanIn(eans)
                    .forEach(product -> existing.add(product.getEan()));

            Set<String> seen = new HashSet<>();
            List<Product> batch = new ArrayList<>();

            for (OpenProductsFactsProduct source : products) {
                String ean = source.code();
                String name = source.productName();

                if (ean == null || ean.isBlank() || name == null
                        || name.isBlank() || existing.contains(ean)
                        || !seen.add(ean)) {
                    skipped++;
                    continue;
                }

                Product product = new Product(ean, name.trim(), details(source));
                batch.add(product);
                product.setNutrition(nutrition(source));
            }

            repository.saveAll(batch);
            imported += batch.size();
            pagesProcessed++;

            if (response.pageCount() != null
                    && response.pageCount() > 0
                    && page >= response.pageCount()) {
                break;
            }
            if (products.size() < pageSize) {
                break;
            }
        }

        return new ImportResult(pagesProcessed, imported, skipped);*/
    //}

    /*private String details(OpenProductsFactsProduct product) {
        return List.of(product.brands(), product.quantity()).stream()
                .filter(value -> value != null && !value.isBlank())
                .collect(java.util.stream.Collectors.joining(" - "));
    }

    private Nutrition nutrition(OpenProductsFactsProduct product) {
        // prefer the old flat `nutriments` structure when available
        var values = product.nutriments();
        if (values != null) {
            return new Nutrition(
                    value(values.calories()),
                    value(values.proteins()),
                    value(values.lipids()),
                    value(values.carbohydrates()),
                    "PER_100G");
        }

        // fallback to the newer nested `nutrition.aggregated_set[0].nutrients` structure
        var nutrition = product.nutrition();
        if (nutrition != null && nutrition.aggregated_set() != null && !nutrition.aggregated_set().isEmpty()) {
            var entry = nutrition.aggregated_set().get(0);
            if (entry != null && entry.nutrients() != null) {
                var n = entry.nutrients();
                Double calories = n.energyKcal() == null ? null : n.energyKcal().value();
                Double proteins = n.proteins() == null ? null : n.proteins().value();
                Double lipids = n.fat() == null ? null : n.fat().value();
                Double carbohydrates = n.carbohydrates() == null ? null : n.carbohydrates().value();
                String per = entry.per() == null ? "PER_100G" : entry.per().toUpperCase();
                return new Nutrition(
                        value(calories),
                        value(proteins),
                        value(lipids),
                        value(carbohydrates),
                        per);
            }
        }

        return new Nutrition(0f, 0f, 0f, 0f, "PER_100G");
    }

    private Float value(Double value) {
        return value == null ? 0f : value.floatValue();
    }*/
}