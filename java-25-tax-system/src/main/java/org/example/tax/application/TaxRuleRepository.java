package org.example.tax.application;

import org.example.tax.domain.ProductCategory;
import org.example.tax.domain.State;
import org.example.tax.domain.TaxRule;

import java.time.Year;
import java.util.Optional;

public interface TaxRuleRepository {

    Optional<TaxRule> findBy(ProductCategory category, State state, Year year);
}
