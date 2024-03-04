package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.NumericFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link NumericFilter} type</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class NumericFilterPredicateBuilder<F extends NumericFilter> extends ComparableFilterPredicateBuilder<F>{

  /**
   * Build a predicates list for filters that extends {@link NumericFilter}.
   * Just like {@link ComparableFilterPredicateBuilder}, but with the numeric method from {@link CriteriaBuilder}
   * @see CriteriaBuilder
   *
   * @param path The criteria path
   * @param builder The criteria builder
   * @param filter The filter, must extends {@link NumericFilter}
   * @param fieldName The field name
   * @return The {@link Predicate} according the filter rules
   */
  public Predicate buildNumericPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);

    Comparable andGreaterThan = getAndGreaterThan(filter);
    if (andGreaterThan != null) {
      if (predicate == null) {
        predicate = builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan());
      } else {
        predicate = builder.and(predicate, builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan()));
      }
    }

    Comparable orGreaterThan = getOrGreaterThan(filter);
    if (orGreaterThan != null) {
      if (predicate == null) {
        predicate = builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan());
      } else {
        predicate = builder.or(predicate, builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan()));
      }
    }

    Comparable andGreaterThanOrEquals = getAndGreaterThanOrEquals(filter);
    if (andGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo());
      } else {
        predicate = builder.and(predicate, builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo()));
      }
    }

    Comparable orGreaterThanOrEquals = getOrGreaterThanOrEquals(filter);
    if (orGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo());
      } else {
        predicate = builder.or(predicate, builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo()));
      }
    }

    Comparable andLessThan = getAndLessThan(filter);
    if (andLessThan != null) {
      if (predicate == null) {
        predicate = builder.lt(path.get(fieldName), (Number) filter.getIsLessThan());
      } else {
        predicate = builder.and(predicate, builder.lt(path.get(fieldName), (Number) filter.getIsLessThan()));
      }
    }

    Comparable orLessThan = getOrLessThan(filter);
    if (orLessThan != null) {
      if (predicate == null) {
        predicate = builder.lt(path.get(fieldName), (Number) filter.getIsLessThan());
      } else {
        predicate = builder.or(predicate, builder.lt(path.get(fieldName), (Number) filter.getIsLessThan()));
      }
    }

    Comparable andLessThanOrEquals = getAndLessThanOrEquals(filter);
    if (andLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo());
      } else {
        predicate = builder.and(predicate, builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo()));
      }
    }

    Comparable orLessThanOrEquals = getOrLessThanOrEquals(filter);
    if (orLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo());
      } else {
        predicate = builder.or(predicate, builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo()));
      }
    }

    Range andBetween = getAndBetween(filter);
    if (andBetween != null) {
      if (predicate == null) {
        predicate = getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName);
      } else {
        predicate = builder.and(predicate, getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName));
      }
    }

    Range orBetween = getOrBetween(filter);
    if (orBetween != null) {
      if (predicate == null) {
        predicate = getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName);
      } else {
        predicate = builder.or(predicate, getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName));
      }
    }

    return predicate;
  }

}
