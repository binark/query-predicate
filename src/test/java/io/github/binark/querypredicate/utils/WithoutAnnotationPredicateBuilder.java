package io.github.binark.querypredicate.utils;

import io.github.binark.querypredicate.builder.PredicateBuilder;
import io.github.binark.querypredicate.filter.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.lang.reflect.Field;

public class WithoutAnnotationPredicateBuilder implements PredicateBuilder {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, Filter filter,
      String fieldName) {
    return null;
  }

  @Override
  public String getFieldNameFromAnnotation(Field field) {
    return null;
  }
}
