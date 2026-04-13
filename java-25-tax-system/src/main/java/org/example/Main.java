package org.example;

import org.example.tax.application.TaxCalculator;
import org.example.tax.domain.Product;
import org.example.tax.domain.ProductCategory;
import org.example.tax.domain.State;
import org.example.tax.infrastructure.InMemoryTaxRuleRepository;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        var calculator = new TaxCalculator(InMemoryTaxRuleRepository.withDefaults());
        var scenarios = List.of(
                new TaxScenario(
                        new Product("Notebook Pro 15", ProductCategory.ELECTRONICS, new BigDecimal("3500.00")),
                        State.SP,
                        Year.of(2025)
                ),
                new TaxScenario(
                        new Product("Organic Rice", ProductCategory.FOOD, new BigDecimal("30.00")),
                        State.MG,
                        Year.of(2025)
                ),
                new TaxScenario(
                        new Product("Java Book", ProductCategory.BOOK, new BigDecimal("120.00")),
                        State.RJ,
                        Year.of(2024)
                )
        );

        System.out.println("Tax simulation");
        System.out.println("==============");

        for (TaxScenario scenario : scenarios) {
            var result = calculator.calculate(scenario.product(), scenario.state(), scenario.year());
            System.out.printf(
                    "%s | %s | %s | base=%s | tax=%s | total=%s%n",
                    scenario.product().name(),
                    scenario.state(),
                    scenario.year(),
                    scenario.product().basePrice(),
                    result.taxAmount(),
                    result.totalPrice()
            );
        }
    }

    private record TaxScenario(Product product, State state, Year year) {
    }
}
