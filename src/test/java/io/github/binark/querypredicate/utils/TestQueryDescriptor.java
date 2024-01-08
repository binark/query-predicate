package io.github.binark.querypredicate.utils;

import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.descriptor.QueryDescriptor;

public class TestQueryDescriptor implements QueryDescriptor {

  @EntityFieldName("name")
  private TestFilter test;
  private TestFilter withoutAnnotation;

  public TestFilter getTest() {
    return test;
  }

  public void setTest(TestFilter test) {
    this.test = test;
  }

  public TestFilter getWithoutAnnotation() {
    return withoutAnnotation;
  }

  public void setWithoutAnnotation(TestFilter withoutAnnotation) {
    this.withoutAnnotation = withoutAnnotation;
  }
}
