package io.github.binark.querypredicate.management;

import io.github.binark.querypredicate.annotation.FilterClass;
import io.github.binark.querypredicate.builder.PredicateBuilder;
import io.github.binark.querypredicate.filter.BaseStringFilter;
import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestFilterPredicateBuilder;
import io.github.binark.querypredicate.testdouble.WithoutAnnotationPredicateBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
                                                                     () -> resolver.resolverPredicateBuilder(TestBaseFilter.class));

      assertTrue(illegalArgumentException.getMessage().contains(TestBaseFilter.class.getSimpleName()));

    TestFilterPredicateBuilder testFilterPredicateBuilder = new TestFilterPredicateBuilder();
    registry.registerPredicateBuilder(testFilterPredicateBuilder);

      PredicateBuilder predicateBuilder = resolver.resolverPredicateBuilder(TestBaseFilter.class);

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
      PredicateBuilder stringPredicateBuilder = resolver.resolverPredicateBuilder(BaseStringFilter.class);

    Assertions.assertNotNull(stringPredicateBuilder);

    TestFilterPredicateBuilder testFilterPredicateBuilder = new TestFilterPredicateBuilder();
      registry.replacePredicateBuilder(BaseStringFilter.class, testFilterPredicateBuilder);

      PredicateBuilder predicateBuilder = resolver.resolverPredicateBuilder(BaseStringFilter.class);

    assertNotNull(predicateBuilder);
    assertEquals(testFilterPredicateBuilder, predicateBuilder);
    assertNotEquals(stringPredicateBuilder, predicateBuilder);
  }
}