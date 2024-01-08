package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.DoubleFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 * <p>The predicate builder for the {@link DoubleFilter} type. Extends all features from {@link NumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DoubleFilterPredicateBuilder extends NumericFilterPredicateBuilder<DoubleFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, DoubleFilter filter,
      String fieldName) {
    List<Predicate> predicates = buildNumericPredicate(path, builder, filter, fieldName);
    return predicates.size() == 1 ? predicates.get(0) : builder.or(predicates.toArray(new Predicate[0]));
  }
}
