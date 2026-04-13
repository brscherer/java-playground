package org.example.tax.domain;

import java.math.BigDecimal;
import java.time.Year;

public record TaxRule(ProductCategory category, State state, Year year, BigDecimal taxRate) {

    public TaxRule {
        if (category == null) {
            throw new IllegalArgumentException("Category is required");
        }
        if (state == null) {
            throw new IllegalArgumentException("State is required");
        }
        if (year == null) {
            throw new IllegalArgumentException("Year is required");
        }
        if (taxRate == null || taxRate.signum() < 0) {
            throw new IllegalArgumentException("Tax rate must be zero or greater");
        }
        taxRate = taxRate.stripTrailingZeros();
    }
}
