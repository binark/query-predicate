package io.github.binark.querypredicate.testdouble;

import io.github.binark.querypredicate.annotation.FilterClass;
import io.github.binark.querypredicate.builder.PredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.Field;

@FilterClass(TestBaseFilter.class)
public class TestFilterPredicateBuilder implements PredicateBuilder<TestBaseFilter> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, TestBaseFilter filter,
      String fieldName) {
    return null;
  }

  @Override
  public String getFieldNameFromAnnotation(Field field) {
    return null;
  }
}
