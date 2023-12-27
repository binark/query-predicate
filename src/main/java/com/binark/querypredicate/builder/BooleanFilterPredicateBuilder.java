package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The predicate builder for the {@link BooleanFilter} type
 */
public class BooleanFilterPredicateBuilder extends AbstractPredicateBuilder<BooleanFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, BooleanFilter filter, String fieldName) {
    List<Predicate> predicates = new ArrayList<>();
    Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);
    predicates.add(predicate);

    if (filter.getTrue() != null) {
      predicates.add(builder.isTrue(path.get(fieldName)));
    }

    if (filter.getFalse() != null) {
      predicates.add(builder.isFalse(path.get(fieldName)));
    }

    return builder.or(predicates.toArray(new Predicate[0]));
  }
}
