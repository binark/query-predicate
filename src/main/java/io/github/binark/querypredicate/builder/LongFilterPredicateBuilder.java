package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.LongFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link LongFilter} type. Extends all features from {@link NumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LongFilterPredicateBuilder extends NumericFilterPredicateBuilder<LongFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, LongFilter filter,
      String fieldName) {
    return buildNumericPredicate(path, builder, filter, fieldName);
  }
}
