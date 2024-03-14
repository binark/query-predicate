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
        predicate = Boolean.TRUE.equals(andTrue) ? builder.isTrue(path.get(fieldName)) : builder.isFalse(path.get(fieldName));
      } else {
        predicate = Boolean.TRUE.equals(andTrue) ? builder.and(predicate, builder.isTrue(path.get(fieldName))) : builder.and(predicate, builder.isFalse(path.get(fieldName)));
      }
    }

    Boolean orTrue = getOrTrue(filter);
    if (orTrue != null) {
      if (predicate == null) {
        predicate = Boolean.TRUE.equals(orTrue) ? builder.isTrue(path.get(fieldName)) : builder.isFalse(path.get(fieldName));
      } else {
        predicate = Boolean.TRUE.equals(orTrue) ? builder.or(predicate, builder.isTrue(path.get(fieldName))) : builder.or(predicate, builder.isFalse(path.get(fieldName)));
      }
    }

    Boolean andFalse = getAndFalse(filter);
    if (andFalse != null) {
      if (predicate == null) {
        predicate = Boolean.TRUE.equals(andFalse) ? builder.isFalse(path.get(fieldName)) : builder.isTrue(path.get(fieldName));
      } else {
        predicate = Boolean.TRUE.equals(andFalse) ? builder.and(predicate, builder.isFalse(path.get(fieldName))) : builder.and(predicate, builder.isTrue(path.get(fieldName)));
      }
    }

    Boolean orFalse = getOrFalse(filter);
    if (orFalse != null) {
      if (predicate == null) {
        predicate = Boolean.TRUE.equals(orFalse) ? builder.isFalse(path.get(fieldName)) : builder.isTrue(path.get(fieldName));
      } else {
        predicate = Boolean.TRUE.equals(orFalse) ? builder.or(predicate, builder.isFalse(path.get(fieldName))) : builder.or(predicate, builder.isTrue(path.get(fieldName)));
      }
    }

    return predicate;
  }

  private Boolean getAndTrue(BooleanFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getTrue();
    }
    return null;
  }

  private Boolean getOrTrue(BooleanFilter filter) {
    Boolean value = filter.getTrue();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getTrue();
    }
    return value;
  }

  private Boolean getAndFalse(BooleanFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getFalse();
    }
    return null;
  }

  private Boolean getOrFalse(BooleanFilter filter) {
    Boolean value = filter.getFalse();
    if (value == null && filter.getOr() != null) {
      return filter.getOr().getFalse();
    }
    return value;
  }
}
