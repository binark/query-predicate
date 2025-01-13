package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDoubleFilter;
import io.github.binark.querypredicate.filter.DoubleFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link BaseDoubleFilter} type. Extends all features from
 * {@link BaseNumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DoubleFilterPredicateBuilder extends NumericFilterPredicateBuilder<DoubleFilter> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, DoubleFilter filter,
      String fieldName) {
    return super.buildPredicate(path, builder, filter, fieldName);
  }
}
