package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.LongFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

public class LongFilterPredicateBuilder extends NumericFilterPredicateBuilder<LongFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, LongFilter filter,
      String fieldName) {
    List<Predicate> predicates = buildNumericPredicate(path, builder, filter, fieldName);
    return predicates.size() == 1 ? predicates.get(0) : builder.or(predicates.toArray(new Predicate[0]));
  }
}
