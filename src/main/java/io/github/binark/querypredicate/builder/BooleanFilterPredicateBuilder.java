package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseBooleanFilter;
import io.github.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The predicate builder for the {@link BaseBooleanFilter} type
 */
public class BooleanFilterPredicateBuilder extends ComparableFilterPredicateBuilder<BooleanFilter> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, BooleanFilter filter, String fieldName) {
    Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
    predicate = addIsTruePredicate(predicate, filter, builder, path, fieldName);
    predicate = addIsFalsePredicate(predicate, filter, builder, path, fieldName);
    return predicate;
  }

  private Predicate addIsTruePredicate(Predicate predicate, BooleanFilter filter, CriteriaBuilder builder, Path path,
                                       String fieldName) {
    Boolean filterTrue = filter.getTrue();
    if (filterTrue != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(filterTrue) ? builder.isTrue(path.get(fieldName)) :
              builder.isFalse(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder);
    }
    Boolean andTrue = getAndTrue(filter);
    if (andTrue != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(andTrue) ? builder.isTrue(path.get(fieldName)) :
              builder.isFalse(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder);
    }
    Boolean orTrue = getOrTrue(filter);
    if (orTrue != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(orTrue) ? builder.isTrue(path.get(fieldName)) :
              builder.isFalse(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  private Predicate addIsFalsePredicate(Predicate predicate, BooleanFilter filter, CriteriaBuilder builder, Path path,
                                        String fieldName) {
    Boolean filterFalse = filter.getFalse();
    if (filterFalse != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(filterFalse) ? builder.isFalse(path.get(fieldName)) :
              builder.isTrue(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder);
    }

    Boolean andFalse = getAndFalse(filter);
    if (andFalse != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(andFalse) ? builder.isFalse(path.get(fieldName)) :
              builder.isTrue(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder);
    }

    Boolean orFalse = getOrFalse(filter);
    if (orFalse != null) {
      Predicate temporaryPredicate = Boolean.TRUE.equals(orFalse) ? builder.isFalse(path.get(fieldName)) :
              builder.isTrue(path.get(fieldName));
      predicate = combinePredicate(predicate, temporaryPredicate, builder, CombineOperator.OR);
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
    if (filter.getOr() != null) {
      return filter.getOr().getTrue();
    }
    return null;
  }

  private Boolean getAndFalse(BooleanFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getFalse();
    }
    return null;
  }

  private Boolean getOrFalse(BooleanFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getFalse();
    }
    return null;
  }
}
