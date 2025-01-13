package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseComparableFilter;
import io.github.binark.querypredicate.filter.ComparableFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>
 * The predicate builder for the {@link BaseComparableFilter} type
 * </p>
 * @param <F> The filter type, should extends {@link BaseComparableFilter}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class ComparableFilterPredicateBuilder<F extends ComparableFilter> extends OperatorFilterPredicateBuilder<F> {

  /**
   * Build a predicates list for filters that implements {@link BaseComparableFilter}
   *
   * @param path The criteria path
   * @param builder The criteria builder
   * @param filter The filter, must implement {@link BaseComparableFilter}
   * @param fieldName The field name
   * @return The {@link Predicate} according the filter rules
   */
  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    Predicate predicate = super.buildBasePredicate(path, builder, filter, fieldName);
    predicate = addGreaterThanPredicate(predicate, filter, builder, path, fieldName);
    predicate = addGreaterThanOrEqualsToPredicate(predicate, filter, builder, path, fieldName);
    predicate = addLessThanPredicate(predicate, filter, builder, path, fieldName);
    predicate = addLessThanOrEqualsToPredicate(predicate, filter, builder, path, fieldName);
    predicate = addBetweenPredicate(predicate, filter, builder, path, fieldName);
    return predicate;
  }

  /**
   * Get the between predicate from the {@link Range} value.
   * Both start and end attributes from range should be not null.
   * It will throw {@link IllegalArgumentException} if one of start or end is null
   *
   * @param path      {@link Path} The criteria path
   * @param builder   {@link CriteriaBuilder} The criteria builder
   * @param range     {@link Range} The range with start and end values
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

  protected Predicate addGreaterThanPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                              Path path, String fieldName) {
    Comparable isGreaterThan = filter.getIsGreaterThan();
    if (isGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.greaterThan(path.get(fieldName), isGreaterThan), builder);
    }
    Comparable andGreaterThan = getAndGreaterThan(filter);
    if (andGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.greaterThan(path.get(fieldName), andGreaterThan), builder);
    }
    Comparable orGreaterThan = getOrGreaterThan(filter);
    if (orGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.greaterThan(path.get(fieldName), orGreaterThan), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addGreaterThanOrEqualsToPredicate(Predicate predicate, F filter,
                                                        CriteriaBuilder builder,
                                                        Path path, String fieldName) {
    Comparable isGreaterThanOrEqualsTo = filter.getIsGreaterThanOrEqualsTo();
    if (isGreaterThanOrEqualsTo != null) {
      predicate = combinePredicate(predicate, builder.greaterThanOrEqualTo(path.get(fieldName),
                                                                           isGreaterThanOrEqualsTo), builder);
    }
    Comparable andGreaterThanOrEquals = getAndGreaterThanOrEquals(filter);
    if (andGreaterThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.greaterThanOrEqualTo(path.get(fieldName),
                                                                           andGreaterThanOrEquals), builder);
    }
    Comparable orGreaterThanOrEquals = getOrGreaterThanOrEquals(filter);
    if (orGreaterThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.greaterThanOrEqualTo(path.get(fieldName),
                                                                           orGreaterThanOrEquals), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addLessThanPredicate(Predicate predicate, F filter,
                                           CriteriaBuilder builder,
                                           Path path, String fieldName) {
    Comparable isLessThan = filter.getIsLessThan();
    if (isLessThan != null) {
      predicate = combinePredicate(predicate, builder.lessThan(path.get(fieldName), isLessThan), builder);
    }
    Comparable andLessThan = getAndLessThan(filter);
    if (andLessThan != null) {
      predicate = combinePredicate(predicate, builder.lessThan(path.get(fieldName), andLessThan), builder);
    }
    Comparable orLessThan = getOrLessThan(filter);
    if (orLessThan != null) {
      predicate = combinePredicate(predicate, builder.lessThan(path.get(fieldName), orLessThan), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addLessThanOrEqualsToPredicate(Predicate predicate, F filter,
                                                     CriteriaBuilder builder,
                                                     Path path, String fieldName) {
    Comparable isLessThanOrEqualsTo = filter.getIsLessThanOrEqualsTo();
    if (isLessThanOrEqualsTo != null) {
      predicate = combinePredicate(predicate, builder.lessThanOrEqualTo(path.get(fieldName), isLessThanOrEqualsTo),
                                   builder);
    }
    Comparable andLessThanOrEquals = getAndLessThanOrEquals(filter);
    if (andLessThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.lessThanOrEqualTo(path.get(fieldName), andLessThanOrEquals),
                                   builder);
    }
    Comparable orLessThanOrEquals = getOrLessThanOrEquals(filter);
    if (orLessThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.lessThanOrEqualTo(path.get(fieldName), orLessThanOrEquals),
                                   builder, CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addBetweenPredicate(Predicate predicate, F filter,
                                          CriteriaBuilder builder,
                                          Path path, String fieldName) {
    Range isBetween = filter.getIsBetween();
    if (isBetween != null) {
      predicate = combinePredicate(predicate, getBetweenPredicate(path, builder, isBetween, fieldName), builder);
    }
    Range andBetween = getAndBetween(filter);
    if (andBetween != null) {
      predicate = combinePredicate(predicate, getBetweenPredicate(path, builder, andBetween, fieldName), builder);
    }
    Range orBetween = getOrBetween(filter);
    if (orBetween != null) {
      predicate = combinePredicate(predicate, getBetweenPredicate(path, builder, orBetween, fieldName), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  protected Comparable getAndGreaterThan(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsGreaterThan();
    }
    return null;
  }

  protected Comparable getOrGreaterThan(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsGreaterThan();
    }
    return null;
  }

  protected Comparable getAndGreaterThanOrEquals(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsGreaterThanOrEqualsTo();
    }
    return null;
  }

  protected Comparable getOrGreaterThanOrEquals(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsGreaterThanOrEqualsTo();
    }
    return null;
  }

  protected Comparable getAndLessThan(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsLessThan();
    }
    return null;
  }

  protected Comparable getOrLessThan(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsLessThan();
    }
    return null;
  }

  protected Comparable getAndLessThanOrEquals(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsLessThanOrEqualsTo();
    }
    return null;
  }

  protected Comparable getOrLessThanOrEquals(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsLessThanOrEqualsTo();
    }
    return null;
  }

  protected Range getAndBetween(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsBetween();
    }
    return null;
  }

  protected Range getOrBetween(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsBetween();
    }
    return null;
  }
}
