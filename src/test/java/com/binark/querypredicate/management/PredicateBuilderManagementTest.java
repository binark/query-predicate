package com.binark.querypredicate.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.binark.querypredicate.annotation.FilterClass;
import com.binark.querypredicate.builder.PredicateBuilder;
import com.binark.querypredicate.filter.StringFilter;
import com.binark.querypredicate.utils.TestFilter;
import com.binark.querypredicate.utils.TestFilterPredicateBuilder;
import com.binark.querypredicate.utils.WithoutAnnotationPredicateBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PredicateBuilderManagementTest {

  private PredicateBuilderRegistry registry = new PredicateBuilderRegistry();

  private PredicateBuilderResolver resolver = new BasePredicateBuilderResolver();

  @AfterEach
  void tearDown() {
    registry = new PredicateBuilderRegistry();
    resolver = new BasePredicateBuilderResolver();
  }

  @Test
  void registerPredicateBuilder() {

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> resolver.resolverPredicateBuilder(TestFilter.class));

    assertTrue(illegalArgumentException.getMessage().contains(TestFilter.class.getSimpleName()));

    TestFilterPredicateBuilder testFilterPredicateBuilder = new TestFilterPredicateBuilder();
    registry.registerPredicateBuilder(testFilterPredicateBuilder);

    PredicateBuilder predicateBuilder = resolver.resolverPredicateBuilder(TestFilter.class);

    assertNotNull(predicateBuilder);
    assertEquals(testFilterPredicateBuilder, predicateBuilder);
  }

  @Test
  void registerPredicateBuilder_Without_Filterclass_annotation() {

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> registry.registerPredicateBuilder(new WithoutAnnotationPredicateBuilder()));

    assertTrue(illegalArgumentException.getMessage().contains(FilterClass.class.getSimpleName()));
    assertTrue(illegalArgumentException.getMessage().contains(WithoutAnnotationPredicateBuilder.class.getSimpleName()));
  }

  @Test
  void replacePredicateBuilder() {
    PredicateBuilder stringPredicateBuilder = resolver.resolverPredicateBuilder(StringFilter.class);

    Assertions.assertNotNull(stringPredicateBuilder);

    TestFilterPredicateBuilder testFilterPredicateBuilder = new TestFilterPredicateBuilder();
    registry.replacePredicateBuilder(StringFilter.class, testFilterPredicateBuilder);

    PredicateBuilder predicateBuilder = resolver.resolverPredicateBuilder(StringFilter.class);

    assertNotNull(predicateBuilder);
    assertEquals(testFilterPredicateBuilder, predicateBuilder);
    assertNotEquals(stringPredicateBuilder, predicateBuilder);
  }
}