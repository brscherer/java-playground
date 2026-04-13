package org.example.tax.infrastructure;

import org.example.tax.application.TaxRuleRepository;
import org.example.tax.domain.ProductCategory;
import org.example.tax.domain.State;
import org.example.tax.domain.TaxRule;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemoryTaxRuleRepository implements TaxRuleRepository {

    private final List<TaxRule> rules;

    public InMemoryTaxRuleRepository() {
        this(List.of());
    }

    public InMemoryTaxRuleRepository(List<TaxRule> rules) {
        this.rules = new ArrayList<>(rules);
    }

    public static InMemoryTaxRuleRepository withDefaults() {
        return new InMemoryTaxRuleRepository(List.of(
                new TaxRule(ProductCategory.ELECTRONICS, State.SP, Year.of(2025), new BigDecimal("0.18")),
                new TaxRule(ProductCategory.ELECTRONICS, State.RJ, Year.of(2025), new BigDecimal("0.17")),
                new TaxRule(ProductCategory.FOOD, State.SP, Year.of(2025), new BigDecimal("0.07")),
                new TaxRule(ProductCategory.FOOD, State.MG, Year.of(2025), new BigDecimal("0.05")),
                new TaxRule(ProductCategory.BOOK, State.RJ, Year.of(2024), new BigDecimal("0.10")),
                new TaxRule(ProductCategory.BOOK, State.RJ, Year.of(2025), new BigDecimal("0.08"))
        ));
    }

    @Override
    public Optional<TaxRule> findBy(ProductCategory category, State state, Year year) {
        return rules.stream()
                .filter(rule -> rule.category() == category)
                .filter(rule -> rule.state() == state)
                .filter(rule -> rule.year().equals(year))
                .findFirst();
    }
}
