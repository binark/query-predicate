package io.github.binark.querypredicate.testdouble;

import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.descriptor.QueryDescriptor;

public class TestQueryDescriptor implements QueryDescriptor {

  @EntityFieldName("name")
  private TestBaseFilter test;
    private TestBaseFilter withoutAnnotation;

    public TestBaseFilter getTest() {
    return test;
  }

    public void setTest(TestBaseFilter test) {
    this.test = test;
  }

    public TestBaseFilter getWithoutAnnotation() {
    return withoutAnnotation;
  }

    public void setWithoutAnnotation(TestBaseFilter withoutAnnotation) {
    this.withoutAnnotation = withoutAnnotation;
  }
}
