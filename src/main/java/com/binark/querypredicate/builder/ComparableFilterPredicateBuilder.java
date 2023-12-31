package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.ComparableFilter;
import com.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

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
   * @return The {@link List} of {@link Predicate} according the filter rules
   */
  public List<Predicate> buildComparablePredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    List<Predicate> predicates = buildBaseFilterPredicate(path, builder, filter, fieldName);

    if (filter.getIsGreaterThan() != null) {
      predicates.add(builder.greaterThan(path.get(fieldName), filter.getIsGreaterThan()));
    }

    if (filter.getIsGreaterThanOrEqualsTo() != null) {
      predicates.add(builder.greaterThanOrEqualTo(path.get(fieldName), filter.getIsGreaterThanOrEqualsTo()));
    }

    if (filter.getIsLessThan() != null) {
      predicates.add(builder.lessThan(path.get(fieldName), filter.getIsLessThan()));
    }

    if (filter.getIsLessThanOrEqualsTo() != null) {
      predicates.add(builder.lessThanOrEqualTo(path.get(fieldName), filter.getIsLessThanOrEqualsTo()));
    }

    if (filter.getIsBetween() != null) {
      predicates.add(getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName));
    }

    return predicates;
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
}
