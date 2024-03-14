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
        predicate = builder.gt(path.get(fieldName), (Number) andGreaterThan);
      } else {
        predicate = builder.and(predicate, builder.gt(path.get(fieldName), (Number) andGreaterThan));
      }
    }

    Comparable orGreaterThan = getOrGreaterThan(filter);
    if (orGreaterThan != null) {
      if (predicate == null) {
        predicate = builder.gt(path.get(fieldName), (Number) orGreaterThan);
      } else {
        predicate = builder.or(predicate, builder.gt(path.get(fieldName), (Number) orGreaterThan));
      }
    }

    Comparable andGreaterThanOrEquals = getAndGreaterThanOrEquals(filter);
    if (andGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.ge(path.get(fieldName), (Number) andGreaterThanOrEquals);
      } else {
        predicate = builder.and(predicate, builder.ge(path.get(fieldName), (Number) andGreaterThanOrEquals));
      }
    }

    Comparable orGreaterThanOrEquals = getOrGreaterThanOrEquals(filter);
    if (orGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.ge(path.get(fieldName), (Number) orGreaterThanOrEquals);
      } else {
        predicate = builder.or(predicate, builder.ge(path.get(fieldName), (Number) orGreaterThanOrEquals));
      }
    }

    Comparable andLessThan = getAndLessThan(filter);
    if (andLessThan != null) {
      if (predicate == null) {
        predicate = builder.lt(path.get(fieldName), (Number) andLessThan);
      } else {
        predicate = builder.and(predicate, builder.lt(path.get(fieldName), (Number) andLessThan));
      }
    }

    Comparable orLessThan = getOrLessThan(filter);
    if (orLessThan != null) {
      if (predicate == null) {
        predicate = builder.lt(path.get(fieldName), (Number) orLessThan);
      } else {
        predicate = builder.or(predicate, builder.lt(path.get(fieldName), (Number) orLessThan));
      }
    }

    Comparable andLessThanOrEquals = getAndLessThanOrEquals(filter);
    if (andLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.le(path.get(fieldName), (Number) andLessThanOrEquals);
      } else {
        predicate = builder.and(predicate, builder.le(path.get(fieldName), (Number) andLessThanOrEquals));
      }
    }

    Comparable orLessThanOrEquals = getOrLessThanOrEquals(filter);
    if (orLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.le(path.get(fieldName), (Number) orLessThanOrEquals);
      } else {
        predicate = builder.or(predicate, builder.le(path.get(fieldName), (Number) orLessThanOrEquals));
      }
    }

    Range andBetween = getAndBetween(filter);
    if (andBetween != null) {
      if (predicate == null) {
        predicate = getBetweenPredicate(path, builder, andBetween, fieldName);
      } else {
        predicate = builder.and(predicate, getBetweenPredicate(path, builder, andBetween, fieldName));
      }
    }

    Range orBetween = getOrBetween(filter);
    if (orBetween != null) {
      if (predicate == null) {
        predicate = getBetweenPredicate(path, builder, orBetween, fieldName);
      } else {
        predicate = builder.or(predicate, getBetweenPredicate(path, builder, orBetween, fieldName));
      }
    }

    return predicate;
  }

}
