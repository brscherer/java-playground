package org.example.tax.domain;

import java.math.BigDecimal;

public record Product(String name, ProductCategory category, BigDecimal basePrice) {

    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product category is required");
        }
        if (basePrice == null || basePrice.signum() < 0) {
            throw new IllegalArgumentException("Base price must be zero or greater");
        }
        basePrice = basePrice.setScale(2);
    }
}
