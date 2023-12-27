package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.ComparableFilter;
import com.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * The predicate builder for the {@link ComparableFilter} type
 * @param <F>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class ComparableFilterPredicateBuilder<F extends ComparableFilter> extends AbstractPredicateBuilder<F>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
    List<Predicate> predicates = new ArrayList<>();
    Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);
    predicates.add(predicate);

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

    return builder.or(predicates.toArray(new Predicate[0]));
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
