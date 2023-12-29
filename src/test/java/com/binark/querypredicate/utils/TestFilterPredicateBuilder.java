package com.binark.querypredicate.utils;

import com.binark.querypredicate.annotation.FilterClass;
import com.binark.querypredicate.builder.PredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.lang.reflect.Field;

@FilterClass(TestFilter.class)
public class TestFilterPredicateBuilder implements PredicateBuilder<TestFilter> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, TestFilter filter,
      String fieldName) {
    return null;
  }

  @Override
  public String getFieldNameFromAnnotation(Field field) {
    return null;
  }
}
