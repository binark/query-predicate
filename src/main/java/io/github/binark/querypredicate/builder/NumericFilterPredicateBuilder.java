package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseNumericFilter;
import io.github.binark.querypredicate.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link BaseNumericFilter} type</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class NumericFilterPredicateBuilder<F extends NumericFilter> extends ComparableFilterPredicateBuilder<F> {

  /**
   * Build a predicate list for filters that extends {@link BaseNumericFilter}.
   * Just like {@link BaseComparableFilterPredicateBuilder}, but with the numeric method from {@link CriteriaBuilder}
   * @see CriteriaBuilder
   *
   * @param path The criteria path
   * @param builder The criteria builder
   * @param filter The filter, must extends {@link BaseNumericFilter}
   * @param fieldName The field name
   * @return The {@link Predicate} according the filter rules
   */
  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    return super.buildPredicate(path, builder, filter, fieldName);
  }

  @Override
  protected Predicate addGreaterThanPredicate(Predicate predicate, F filter, CriteriaBuilder builder, Path path,
                                              String fieldName) {
    Comparable isGreaterThan = filter.getIsGreaterThan();
    if (isGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.gt(path.get(fieldName), (Number) isGreaterThan), builder);
    }
    Comparable andGreaterThan = getAndGreaterThan(filter);
    if (andGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.gt(path.get(fieldName), (Number) andGreaterThan),
                                   builder);
    }
    Comparable orGreaterThan = getOrGreaterThan(filter);
    if (orGreaterThan != null) {
      predicate = combinePredicate(predicate, builder.gt(path.get(fieldName), (Number) orGreaterThan), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  @Override
  protected Predicate addGreaterThanOrEqualsToPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                                        Path path, String fieldName) {
    Comparable isGreaterThanOrEqualsTo = filter.getIsGreaterThanOrEqualsTo();
    if (isGreaterThanOrEqualsTo != null) {
      predicate = combinePredicate(predicate, builder.ge(path.get(fieldName),
                                                         (Number) isGreaterThanOrEqualsTo), builder);
    }
    Comparable andGreaterThanOrEquals = getAndGreaterThanOrEquals(filter);
    if (andGreaterThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.ge(path.get(fieldName),
                                                         (Number) andGreaterThanOrEquals), builder);
    }
    Comparable orGreaterThanOrEquals = getOrGreaterThanOrEquals(filter);
    if (orGreaterThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.ge(path.get(fieldName),
                                                         (Number) orGreaterThanOrEquals), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  @Override
  protected Predicate addLessThanPredicate(Predicate predicate, F filter, CriteriaBuilder builder, Path path,
                                           String fieldName) {
    Comparable isLessThan = filter.getIsLessThan();
    if (isLessThan != null) {
      predicate = combinePredicate(predicate, builder.lt(path.get(fieldName), (Number) isLessThan), builder);
    }
    Comparable andLessThan = getAndLessThan(filter);
    if (andLessThan != null) {
      predicate = combinePredicate(predicate, builder.lt(path.get(fieldName), (Number) andLessThan), builder);
    }
    Comparable orLessThan = getOrLessThan(filter);
    if (orLessThan != null) {
      predicate = combinePredicate(predicate, builder.lt(path.get(fieldName), (Number) orLessThan), builder,
                                   CombineOperator.OR);
    }
    return predicate;
  }

  @Override
  protected Predicate addLessThanOrEqualsToPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                                     Path path, String fieldName) {
    Comparable isLessThanOrEqualsTo = filter.getIsLessThanOrEqualsTo();
    if (isLessThanOrEqualsTo != null) {
      predicate = combinePredicate(predicate, builder.le(path.get(fieldName), (Number) isLessThanOrEqualsTo),
                                   builder);
    }
    Comparable andLessThanOrEquals = getAndLessThanOrEquals(filter);
    if (andLessThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.le(path.get(fieldName), (Number) andLessThanOrEquals),
                                   builder);
    }
    Comparable orLessThanOrEquals = getOrLessThanOrEquals(filter);
    if (orLessThanOrEquals != null) {
      predicate = combinePredicate(predicate, builder.le(path.get(fieldName), (Number) orLessThanOrEquals),
                                   builder, CombineOperator.OR);
    }
    return predicate;
  }
}
