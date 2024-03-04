package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.FloatFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link FloatFilter} type. Extends all features from {@link NumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class FloatFilterPredicateBuilder extends NumericFilterPredicateBuilder<FloatFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, FloatFilter filter,
      String fieldName) {
    return buildNumericPredicate(path, builder, filter, fieldName);
  }
}
