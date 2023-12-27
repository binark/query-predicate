package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * The predicate builder for the {@link NumericFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class NumericFilterPredicateBuilder extends ComparableFilterPredicateBuilder<NumericFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, NumericFilter filter, String fieldName) {
    List<Predicate> predicates = new ArrayList<>();
    Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);
    predicates.add(predicate);

    if (filter.getIsGreaterThan() != null) {
      predicates.add(builder.gt(path.get(fieldName), (Number) filter.getIsGreaterThan()));
    }

    if (filter.getIsGreaterThanOrEqualsTo() != null) {
      predicates.add(builder.ge(path.get(fieldName), (Number) filter.getIsGreaterThanOrEqualsTo()));
    }

    if (filter.getIsLessThan() != null) {
      predicates.add(builder.lt(path.get(fieldName), (Number) filter.getIsLessThan()));
    }

    if (filter.getIsLessThanOrEqualsTo() != null) {
      predicates.add(builder.le(path.get(fieldName), (Number) filter.getIsLessThanOrEqualsTo()));
    }

    if (filter.getIsBetween() != null) {
      predicates.add(getBetweenPredicate(path, builder, filter.getIsBetween(), fieldName));
    }

    return builder.or(predicates.toArray(new Predicate[0]));
  }

}
