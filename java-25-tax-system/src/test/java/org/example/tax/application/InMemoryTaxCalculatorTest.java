package org.example.tax.application;

import org.example.tax.domain.Product;
import org.example.tax.domain.ProductCategory;
import org.example.tax.domain.State;
import org.example.tax.domain.TaxResult;
import org.example.tax.infrastructure.InMemoryTaxRuleRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTaxCalculatorTest {

    @Test
    void calculatesTaxForCategoryStateAndYear() {
        var repository = InMemoryTaxRuleRepository.withDefaults();
        var calculator = new TaxCalculator(repository);
        var product = new Product(
                "Notebook Pro 15",
                ProductCategory.ELECTRONICS,
                new BigDecimal("3500.00")
        );

        TaxResult result = calculator.calculate(product, State.SP, Year.of(2025));

        assertEquals(new BigDecimal("630.00"), result.taxAmount());
        assertEquals(new BigDecimal("4130.00"), result.totalPrice());
        assertEquals(new BigDecimal("0.18"), result.taxRate());
    }

    @Test
    void usesDifferentRateForSameCategoryAcrossYears() {
        var repository = InMemoryTaxRuleRepository.withDefaults();
        var calculator = new TaxCalculator(repository);
        var product = new Product(
                "Java Book",
                ProductCategory.BOOK,
                new BigDecimal("120.00")
        );

        TaxResult result2024 = calculator.calculate(product, State.RJ, Year.of(2024));
        TaxResult result2025 = calculator.calculate(product, State.RJ, Year.of(2025));

        assertEquals(new BigDecimal("12.00"), result2024.taxAmount());
        assertEquals(new BigDecimal("9.60"), result2025.taxAmount());
    }

    @Test
    void throwsWhenThereIsNoTaxRuleForTheRequestedScenario() {
        var repository = new InMemoryTaxRuleRepository();
        var calculator = new TaxCalculator(repository);
        var product = new Product(
                "Organic Rice",
                ProductCategory.FOOD,
                new BigDecimal("30.00")
        );

        assertThrows(
                TaxRuleNotFoundException.class,
                () -> calculator.calculate(product, State.MG, Year.of(2026))
        );
    }
}
