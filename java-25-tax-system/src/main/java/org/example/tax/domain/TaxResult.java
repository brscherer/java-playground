package org.example.tax.domain;

import java.math.BigDecimal;

public record TaxResult(Product product, State state, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal totalPrice) {

    public TaxResult {
        if (product == null) {
            throw new IllegalArgumentException("Product is required");
        }
        if (state == null) {
            throw new IllegalArgumentException("State is required");
        }
        if (taxRate == null || taxAmount == null || totalPrice == null) {
            throw new IllegalArgumentException("Tax result values are required");
        }
        taxAmount = taxAmount.setScale(2);
        totalPrice = totalPrice.setScale(2);
    }
}
