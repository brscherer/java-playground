package org.example.tax.application;

import org.example.tax.domain.Product;
import org.example.tax.domain.State;
import org.example.tax.domain.TaxResult;

import java.math.RoundingMode;
import java.time.Year;

public final class TaxCalculator {

    private final TaxRuleRepository repository;

    public TaxCalculator(TaxRuleRepository repository) {
        this.repository = repository;
    }

    public TaxResult calculate(Product product, State state, Year year) {
        var rule = repository.findBy(product.category(), state, year)
                .orElseThrow(() -> new TaxRuleNotFoundException(product.category(), state, year));

        var taxAmount = product.basePrice()
                .multiply(rule.taxRate())
                .setScale(2, RoundingMode.HALF_UP);
        var totalPrice = product.basePrice()
                .add(taxAmount)
                .setScale(2, RoundingMode.HALF_UP);

        return new TaxResult(product, state, rule.taxRate(), taxAmount, totalPrice);
    }
}
