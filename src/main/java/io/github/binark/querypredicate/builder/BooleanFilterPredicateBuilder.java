package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The predicate builder for the {@link BooleanFilter} type
 */
public class BooleanFilterPredicateBuilder extends ComparableFilterPredicateBuilder<BooleanFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, BooleanFilter filter, String fieldName) {
    List<Predicate> predicates = buildComparablePredicate(path, builder, filter, fieldName);

    if (filter.getTrue() != null) {
      predicates.add(builder.isTrue(path.get(fieldName)));
    }

    if (filter.getFalse() != null) {
      predicates.add(builder.isFalse(path.get(fieldName)));
    }

    return predicates.size() == 1 ? predicates.get(0) : builder.or(predicates.toArray(new Predicate[0]));
  }
}
