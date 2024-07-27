package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.utils.TestFilter;
import io.github.binark.querypredicate.utils.TestObject;
import io.github.binark.querypredicate.utils.TestQueryDescriptor;
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
  @Mock
  Predicate predicateMock;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  @Test
  void buildBaseFilterPredicate_Is_Equals() {
    TestObject value = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(value);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_And_Is_Equals() {
    TestObject value = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsEquals(value);
    testFilter.setAnd(andTestFilter);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_And_With_Normal_Is_Equals() {
    TestObject andValue = new TestObject("andEquals");
    TestObject value = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(value);
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsEquals(andValue);
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    ComparisonPredicate andComparisonPredicate = (ComparisonPredicate) expressions.get(0);
    assertNotNull(andComparisonPredicate);
    assertEquals(ComparisonOperator.EQUAL, andComparisonPredicate.getComparisonOperator());
    LiteralExpression<?> andLiteralExpression = (LiteralExpression<?>) andComparisonPredicate.getRightHandOperand();
    assertNotNull(andLiteralExpression);
    assertEquals(value, andLiteralExpression.getLiteral());

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
    assertNotNull(comparisonPredicate);
    assertEquals(ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());
    LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(rightHandOperand);
    assertEquals(andValue, rightHandOperand.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Different() {
    TestObject value = new TestObject("different");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsDifferent(value);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Different_With_Not_Null_Predicate() {
    TestObject differentValue = new TestObject("different");
    TestObject equalsValue = new TestObject("equals");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(equalsValue);
    testFilter.setIsDifferent(differentValue);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
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
  void buildBaseFilterPredicate_And_Is_Different() {
    TestObject value = new TestObject("different");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsDifferent(value);
    TestFilter testFilter = new TestFilter();
    testFilter.setAnd(andTestFilter);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_Different() {
    TestObject andValue = new TestObject("andDifferent");
    TestObject value = new TestObject("different");
    when(path.getJavaType()).thenReturn(String.class);
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsDifferent(andValue);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsDifferent(value);
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());

    ComparisonPredicate andComparisonPredicate = (ComparisonPredicate) expressions.get(0);
    assertNotNull(andComparisonPredicate);
    assertEquals(ComparisonOperator.NOT_EQUAL, andComparisonPredicate.getComparisonOperator());
    LiteralExpression andLiteralExpression = (LiteralExpression) andComparisonPredicate.getRightHandOperand();
    assertNotNull(andLiteralExpression);
    assertEquals(value, andLiteralExpression.getLiteral());

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
    assertNotNull(comparisonPredicate);
    assertEquals(ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());
    LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(rightHandOperand);
    assertEquals(andValue, rightHandOperand.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_True() {
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(true);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_True_With_Not_Null_Predicate() {
    when(path.getJavaType()).thenReturn(String.class);
    TestObject equalsValue = new TestObject("equals");
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(equalsValue);
    testFilter.setNull(true);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
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
  void buildBaseFilterPredicate_And_Is_Null_True() {
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setNull(true);
    TestFilter testFilter = new TestFilter();
    testFilter.setAnd(andTestFilter);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_Null_True() {
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setNull(true);
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(true);
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());
    assertTrue(expressions.stream().allMatch(expression -> expression instanceof NullnessPredicate));
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_False() {
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(false);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_False_With_Not_Null_Predicate() {
    when(path.getJavaType()).thenReturn(String.class);
    TestObject equalsValue = new TestObject("equals");
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(equalsValue);
    testFilter.setNull(false);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
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
  void buildBaseFilterPredicate_And_Is_Null_False() {
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setNull(false);
    TestFilter testFilter = new TestFilter();
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Normal_Is_Null_False() {
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setNull(false);
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(false);
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(AND, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());
    assertTrue(expressions.stream().allMatch(expression -> expression instanceof NegatedPredicateWrapper));
  }

  @Test
  void buildBaseFilterPredicate_Is_In() {
    when(path.get(anyString())).thenReturn(fieldPath);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsIn(values);
    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    verify(path).get(FIELD_NAME);
    verify(fieldPath).in(values);
  }

  @Test
  void buildBaseFilterPredicate_Is_In_With_Not_Null_Predicate() {
    when(path.getJavaType()).thenReturn(String.class);
    when(path.in(anyCollection())).thenReturn(predicateMock);
    TestObject equalsValue = new TestObject("equals");
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(equalsValue);
    testFilter.setIsIn(values);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
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
  void buildBaseFilterPredicate_And_Is_In() {
    when(path.get(anyString())).thenReturn(fieldPath);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsIn(values);
    TestFilter testFilter = new TestFilter();
    testFilter.setAnd(andTestFilter);
    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);
    verify(path).get(FIELD_NAME);
    verify(fieldPath).in(values);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Normal_Is_In() {
    when(path.get(anyString())).thenReturn(fieldPath);
    List<TestObject> andValues = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    List<TestObject> value = Arrays.asList(new TestObject("def"), new TestObject("lmn"));
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsIn(andValues);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsIn(value);
    testFilter.setAnd(andTestFilter);

    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    verify(path, times(2)).get(FIELD_NAME);
    verify(fieldPath).in(andValues);
    verify(fieldPath).in(value);
  }

  @Test
  void buildBaseFilterPredicate_Is_Not_In() {
    when(path.in(anyCollection())).thenReturn(predicateMock);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsNotIn(values);
    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    verify(path).in(values);
    verify(predicateMock).not();
  }

  @Test
  void buildBaseFilterPredicate_Is_Not_In_With_Not_Null_Predicate() {
    when(path.getJavaType()).thenReturn(String.class);
    when(path.in(anyCollection())).thenReturn(predicateMock);
    TestObject equalsValue = new TestObject("equals");
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(equalsValue);
    testFilter.setIsNotIn(values);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
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
  void buildBaseFilterPredicate_And_Is_Not_In() {
    when(path.get(anyString())).thenReturn(fieldPath);
    when(fieldPath.in(anyCollection())).thenReturn(predicateMock);
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsNotIn(values);
    TestFilter testFilter = new TestFilter();
    testFilter.setAnd(andTestFilter);

    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    verify(path).get(FIELD_NAME);
    verify(fieldPath).in(values);
    verify(predicateMock).not();
  }

  @Test
  void buildBaseFilterPredicate_And_With_Normal_Is_Not_In() {
    when(path.get(anyString())).thenReturn(fieldPath);
    when(fieldPath.in(anyCollection())).thenReturn(predicateMock);

    List<TestObject> andValues = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    List<TestObject> normalValues = Arrays.asList(new TestObject("def"), new TestObject("lmn"));
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsNotIn(andValues);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsNotIn(normalValues);
    testFilter.setAnd(andTestFilter);

    predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    verify(path, times(2)).get(FIELD_NAME);
    verify(fieldPath).in(normalValues);
    verify(fieldPath).in(andValues);
    verify(predicateMock, times(2)).not();
  }

  @Test
  void getFieldNameFromAnnotation() throws NoSuchFieldException {
    TestQueryDescriptor testQueryDescriptor = new TestQueryDescriptor();
    testQueryDescriptor.setTest(new TestFilter());
    String fieldName = predicateBuilder.getFieldNameFromAnnotation(
        testQueryDescriptor.getClass().getDeclaredField("test"));

    assertNotNull(fieldName);
    assertEquals("name", fieldName);
  }

  @Test
  void getFieldNameFromAnnotation_MissingAnnotation() throws NoSuchFieldException {
    TestQueryDescriptor testQueryDescriptor = new TestQueryDescriptor();
    testQueryDescriptor.setWithoutAnnotation(new TestFilter());

    String fieldName = "withoutAnnotation";
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> predicateBuilder.getFieldNameFromAnnotation(
            testQueryDescriptor.getClass().getDeclaredField(fieldName)));

    assertTrue(illegalArgumentException.getMessage().contains(EntityFieldName.class.getSimpleName()));
    assertTrue(illegalArgumentException.getMessage().contains(fieldName));
  }
}