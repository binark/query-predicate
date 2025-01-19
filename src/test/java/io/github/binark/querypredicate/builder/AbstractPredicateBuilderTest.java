package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestObject;
import io.github.binark.querypredicate.testdouble.TestQueryDescriptor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.hibernate.query.criteria.internal.predicate.NullnessPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AbstractPredicateBuilderTest {

  public static final String FIELD_NAME = "fieldName";
  public static final String OR = "OR";
  public static final String AND = "AND";

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private AbstractPredicateBuilder predicateBuilder;

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;
  @Mock
  Path fieldPath;
  @Mock(answer = Answers.RETURNS_SELF)
  Predicate predicateMock;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  @Test
  void buildIsEqualsPredicate() {
    TestObject value = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(value);
    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildIsDifferentPredicate() {
    TestObject value = new TestObject("different");
    when(path.getJavaType()).thenReturn(String.class);
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsDifferent(value);
    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildCombinedIsEqualsAndIsDifferentPredicates() {
    TestObject differentValue = new TestObject("different");
    TestObject equalsValue = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(equalsValue);
    testBaseFilter.setIsDifferent(differentValue);

    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    ComparisonPredicate equalsComparisonPredicate = (ComparisonPredicate) expressions.get(0);
    assertEquals(ComparisonOperator.EQUAL, equalsComparisonPredicate.getComparisonOperator());

    LiteralExpression equalsLiteralExpression = (LiteralExpression) equalsComparisonPredicate.getRightHandOperand();
    assertNotNull(equalsLiteralExpression);
    assertEquals(equalsValue, equalsLiteralExpression.getLiteral());

    ComparisonPredicate differentComparisonPredicate = (ComparisonPredicate) expressions.get(1);
    assertEquals(ComparisonOperator.NOT_EQUAL, differentComparisonPredicate.getComparisonOperator());

    LiteralExpression differentLiteralExpression = (LiteralExpression) differentComparisonPredicate.getRightHandOperand();
    assertNotNull(differentLiteralExpression);
    assertEquals(differentValue, differentLiteralExpression.getLiteral());
  }

  @Test
  void buildIsNullTruePredicate() {
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setNull(true);
    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildCombinedIsEqualsAndIsNullTruePredicates() {
    when(path.getJavaType()).thenReturn(String.class);
    TestObject equalsValue = new TestObject("equals");
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(equalsValue);
    testBaseFilter.setNull(true);

    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    NullnessPredicate nullnessPredicate = (NullnessPredicate) expressions.get(1);
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildIsNullFalsePredicate() {
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setNull(false);
    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildCombinedIsEqualsAndIsNullFalsePredicates() {
    when(path.getJavaType()).thenReturn(String.class);
    TestObject equalsValue = new TestObject("equals");
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(equalsValue);
    testBaseFilter.setNull(false);

    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) expressions.get(1);
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildIsInPredicate() {
    when(path.get(anyString())).thenReturn(fieldPath);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsIn(values);
    predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                        FIELD_NAME);

    verify(path).get(FIELD_NAME);
    verify(fieldPath).in(values);
  }

  @Test
  void buildCombinedIsEqualsAndIsInPredicates() {
    when(path.getJavaType()).thenReturn(String.class);
    when(path.in(anyCollection())).thenReturn(predicateMock);
    TestObject equalsValue = new TestObject("equals");
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(equalsValue);
    testBaseFilter.setIsIn(values);

    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    Predicate inPredicate = (Predicate) expressions.get(1);

    verify(path).in(values);
    assertEquals(predicateMock, inPredicate);
  }

  @Test
  void buildIsNotInPredicate() {
    when(path.in(anyCollection())).thenReturn(predicateMock);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsNotIn(values);
    predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                        FIELD_NAME);

    verify(path).in(values);
    verify(predicateMock).not();
  }

  @Test
  void buildCombinedIsEqualsAndIsNotNullPredicates() {
    when(path.getJavaType()).thenReturn(String.class);
    when(path.in(anyCollection())).thenReturn(predicateMock);
    TestObject equalsValue = new TestObject("equals");
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestBaseFilter testBaseFilter = new TestBaseFilter();
    testBaseFilter.setIsEquals(equalsValue);
    testBaseFilter.setIsNotIn(values);

    Predicate predicate = predicateBuilder.buildBasePredicate(path, criteriaBuilder, testBaseFilter,
                                                              FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    verify(path).in(values);
    verify(predicateMock).not();
  }

  @Test
  void getFieldNameFromAnnotation() throws NoSuchFieldException {
    TestQueryDescriptor testQueryDescriptor = new TestQueryDescriptor();
    testQueryDescriptor.setTest(new TestBaseFilter());
    String fieldName = predicateBuilder.getFieldNameFromAnnotation(
        testQueryDescriptor.getClass().getDeclaredField("test"));

    assertNotNull(fieldName);
    assertEquals("name", fieldName);
  }

  @Test
  void getFieldNameFromAnnotationMissingAnnotation() {
    TestQueryDescriptor testQueryDescriptor = new TestQueryDescriptor();
    testQueryDescriptor.setWithoutAnnotation(new TestBaseFilter());

    String fieldName = "withoutAnnotation";
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> predicateBuilder.getFieldNameFromAnnotation(
            testQueryDescriptor.getClass().getDeclaredField(fieldName)));

    assertTrue(illegalArgumentException.getMessage().contains(EntityFieldName.class.getSimpleName()));
    assertTrue(illegalArgumentException.getMessage().contains(fieldName));
  }
}