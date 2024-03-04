package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The predicate builder for the {@link BooleanFilter} type
 */
public class BooleanFilterPredicateBuilder extends ComparableFilterPredicateBuilder<BooleanFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, BooleanFilter filter, String fieldName) {
    Predicate predicate = buildComparablePredicate(path, builder, filter, fieldName);

    Boolean andTrue = getAndTrue(filter);
    if (andTrue != null) {
      if (predicate == null) {
        predicate = builder.isTrue(path.get(fieldName));
      } else {
        predicate = builder.and(predicate, builder.isTrue(path.get(fieldName)));
      }
    }

    Boolean orTrue = getOrTrue(filter);
    if (orTrue != null) {
      if (predicate == null) {
        predicate = builder.isTrue(path.get(fieldName));
      } else {
        predicate = builder.or(predicate, builder.isTrue(path.get(fieldName)));
      }
    }

    Boolean andFalse = getAndFalse(filter);
    if (andFalse != null) {
      if (predicate == null) {
        predicate = builder.isFalse(path.get(fieldName));
      } else {
        predicate = builder.and(predicate, builder.isFalse(path.get(fieldName)));
      }
    }

    Boolean orFalse = getOrFalse(filter);
    if (orFalse != null) {
      if (predicate == null) {
        predicate = builder.isFalse(path.get(fieldName));
      } else {
        predicate = builder.or(predicate, builder.isFalse(path.get(fieldName)));
      }
    }

    return predicate;
  }

  private Boolean getAndTrue(BooleanFilter filter) {
    Boolean value = filter.getTrue();
    if (value == null && filter.getAnd() != null) {
      value = filter.getAnd().getTrue();
    }
    return value;
  }

  private Boolean getOrTrue(BooleanFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getTrue();
    }
    return null;
  }

  private Boolean getAndFalse(BooleanFilter filter) {
    Boolean value = filter.getFalse();
    if (value == null && filter.getAnd() != null) {
      value = filter.getAnd().getFalse();
    }
    return value;
  }

  private Boolean getOrFalse(BooleanFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getFalse();
    }
    return null;
  }
}
