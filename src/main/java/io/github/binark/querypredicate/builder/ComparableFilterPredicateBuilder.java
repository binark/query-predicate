package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.ComparableFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>
 * The predicate builder for the {@link ComparableFilter} type
 * </p>
 * @param <F> The filter type, should extends {@link ComparableFilter}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class ComparableFilterPredicateBuilder<F extends ComparableFilter> extends AbstractPredicateBuilder<F>{

  /**
   * Build a predicates list for filters that implements {@link ComparableFilter}
   *
   * @param path The criteria path
   * @param builder The criteria builder
   * @param filter The filter, must implement {@link ComparableFilter}
   * @param fieldName The field name
   * @return The {@link Predicate} according the filter rules
   */
  public Predicate buildComparablePredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);

    Comparable andGreaterThan = getAndGreaterThan(filter);
    if (andGreaterThan != null) {
      if (predicate == null) {
        predicate = builder.greaterThan(path.get(fieldName), andGreaterThan);
      } else {
        predicate = builder.and(predicate, builder.greaterThan(path.get(fieldName), andGreaterThan));
      }
    }

    Comparable orGreaterThan = getOrGreaterThan(filter);
    if (orGreaterThan != null) {
      if (predicate == null) {
        predicate = builder.greaterThan(path.get(fieldName), orGreaterThan);
      } else {
        predicate = builder.or(predicate, builder.greaterThan(path.get(fieldName), orGreaterThan));
      }
    }

    Comparable andGreaterThanOrEquals = getAndGreaterThanOrEquals(filter);
    if (andGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.greaterThanOrEqualTo(path.get(fieldName), andGreaterThanOrEquals);
      } else {
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(path.get(fieldName), andGreaterThanOrEquals));
      }
    }

    Comparable orGreaterThanOrEquals = getOrGreaterThanOrEquals(filter);
    if (orGreaterThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.greaterThanOrEqualTo(path.get(fieldName), orGreaterThanOrEquals);
      } else {
        predicate = builder.or(predicate, builder.greaterThanOrEqualTo(path.get(fieldName), orGreaterThanOrEquals));
      }
    }

    Comparable andLessThan = getAndLessThan(filter);
    if (andLessThan != null) {
      if (predicate == null) {
        predicate = builder.lessThan(path.get(fieldName), andLessThan);
      } else {
        predicate = builder.and(predicate, builder.lessThan(path.get(fieldName), andLessThan));
      }
    }

    Comparable orLessThan = getOrLessThan(filter);
    if (orLessThan != null) {
      if (predicate == null) {
        predicate = builder.lessThan(path.get(fieldName), orLessThan);
      } else {
        predicate = builder.or(predicate, builder.lessThan(path.get(fieldName), orLessThan));
      }
    }

    Comparable andLessThanOrEquals = getAndLessThanOrEquals(filter);
    if (andLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.lessThanOrEqualTo(path.get(fieldName), andLessThanOrEquals);
      } else {
        predicate = builder.and(predicate, builder.lessThanOrEqualTo(path.get(fieldName), andLessThanOrEquals));
      }
    }

    Comparable orLessThanOrEquals = getOrLessThanOrEquals(filter);
    if (orLessThanOrEquals != null) {
      if (predicate == null) {
        predicate = builder.lessThanOrEqualTo(path.get(fieldName), orLessThanOrEquals);
      } else {
        predicate = builder.or(predicate, builder.lessThanOrEqualTo(path.get(fieldName), orLessThanOrEquals));
      }
    }

    Range andBetween = getAndBetween(filter);
    if (andBetween != null) {
      Predicate temporaryPredicate = getBetweenPredicate(path, builder, andBetween, fieldName);
      if (predicate == null) {
        predicate = temporaryPredicate;
      } else {
        predicate = builder.and(predicate, temporaryPredicate);
      }
    }

    Range orBetween = getOrBetween(filter);
    if (orBetween != null) {
      Predicate temporaryPredicate = getBetweenPredicate(path, builder, orBetween, fieldName);
      if (predicate == null) {
        predicate = temporaryPredicate;
      } else {
        predicate = builder.or(predicate, temporaryPredicate);
      }
    }

    return predicate;
  }

  /**
   * Get the between predicate from the {@link Range} value.
   * Both start and end attributes from range should be not null.
   * It will throw {@link IllegalArgumentException} if one of start or end is null
   *
   * @param path {@link Path} The criteria path
   * @param builder {@link CriteriaBuilder} The criteria builder
   * @param range {@link Range} The range with start and end values
   * @param fieldName The field name
   * @return The {@link Predicate} for between rule.
   */
  protected Predicate getBetweenPredicate(Path path, CriteriaBuilder builder, Range range, String fieldName) {
    Comparable start = range.getStart();
    Comparable end = range.getEnd();
    if (start == null || end == null) {
      throw new IllegalArgumentException("The between field must have both start and end value");
    }
    return builder.between(path.get(fieldName), start, end);
  }

  protected Comparable getAndGreaterThan(ComparableFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsGreaterThan();
    }
    return null;
  }

  protected Comparable getOrGreaterThan(ComparableFilter filter) {
    Comparable value = filter.getIsGreaterThan();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsGreaterThan();
    }
    return value;
  }

  protected Comparable getAndGreaterThanOrEquals(ComparableFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsGreaterThanOrEqualsTo();
    }
    return null;
  }

  protected Comparable getOrGreaterThanOrEquals(ComparableFilter filter) {
    Comparable value = filter.getIsGreaterThanOrEqualsTo();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsGreaterThanOrEqualsTo();
    }
    return value;
  }

  protected Comparable getAndLessThan(ComparableFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsLessThan();
    }
    return null;
  }

  protected Comparable getOrLessThan(ComparableFilter filter) {
    Comparable value = filter.getIsLessThan();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsLessThan();
    }
    return value;
  }

  protected Comparable getAndLessThanOrEquals(ComparableFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsLessThanOrEqualsTo();
    }
    return null;
  }

  protected Comparable getOrLessThanOrEquals(ComparableFilter filter) {
    Comparable value = filter.getIsLessThanOrEqualsTo();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsLessThanOrEqualsTo();
    }
    return value;
  }

  protected Range getAndBetween(ComparableFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsBetween();
    }
    return null;
  }

  protected Range getOrBetween(ComparableFilter filter) {
    Range value = filter.getIsBetween();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsBetween();
    }
    return value;
  }
}
