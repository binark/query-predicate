package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 * The predicate builder for the {@link NumericFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class NumericFilterPredicateBuilder extends ComparableFilterPredicateBuilder<NumericFilter>{

  /**
   * Build a predicates list for filters that implement {@link NumericFilter}.
   * Just like {@link ComparableFilterPredicateBuilder}, but with the numeric method from {@link CriteriaBuilder}
   * @see CriteriaBuilder
   *
   * @param path The criteria path
   * @param builder The criteria builder
   * @param filter The filter, must implement {@link NumericFilter}
   * @param fieldName The field name
   * @return The {@link List} of {@link Predicate} according the filter rules
   */
  public List<Predicate> buildNumericPredicate(Path path, CriteriaBuilder builder, NumericFilter filter, String fieldName) {
    List<Predicate> predicates = buildBaseFilterPredicate(path, builder, filter, fieldName);

    if (filter.getIsGreaterThan() != null) {
      predicates.add(builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan()));
    }

    if (filter.getIsGreaterThanOrEqualsTo() != null) {
      predicates.add(builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo()));
    }

    if (filter.getIsLessThan() != null) {
      predicates.add(builder.lt(path.get(fieldName), (Number) filter.getIsLessThan()));
    }

    if (filter.getIsLessThanOrEqualsTo() != null) {
      predicates.add(builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo()));
    }

    if (filter.getIsBetween() != null) {
      predicates.add(getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName));
    }

    return predicates;
  }

}
