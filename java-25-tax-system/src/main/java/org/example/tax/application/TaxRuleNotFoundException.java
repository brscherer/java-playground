package org.example.tax.application;

import org.example.tax.domain.ProductCategory;
import org.example.tax.domain.State;

import java.time.Year;

public final class TaxRuleNotFoundException extends RuntimeException {

    public TaxRuleNotFoundException(ProductCategory category, State state, Year year) {
        super("No tax rule found for category=%s, state=%s, year=%s".formatted(category, state, year));
    }
}
