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
import org.hibernate.query.criteria.internal.predicate.*;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  @Test
  void buildBaseFilterPredicate_Is_Equals() {
    TestObject value = new TestObject("equals");
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
  void buildBaseFilterPredicate_And_With_Or_Is_Equals() {
    TestObject andValue = new TestObject("andEquals");
    TestObject orValue = new TestObject("orEquals");
    Mockito.when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(orValue);
    TestFilter andTestFilter = new TestFilter();
    andTestFilter.setIsEquals(andValue);
    testFilter.setAnd(andTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertEquals(2, expressions.size());

    ComparisonPredicate andComparisonPredicate = (ComparisonPredicate) expressions.get(0);
    assertNotNull(andComparisonPredicate);
    assertEquals(ComparisonOperator.EQUAL, andComparisonPredicate.getComparisonOperator());
    LiteralExpression andLiteralExpression = (LiteralExpression) andComparisonPredicate.getRightHandOperand();
    assertNotNull(andLiteralExpression);
    assertEquals(andValue, andLiteralExpression.getLiteral());

    ComparisonPredicate orComparisonPredicate = (ComparisonPredicate) expressions.get(1);
    assertNotNull(orComparisonPredicate);
    assertEquals(ComparisonOperator.EQUAL, orComparisonPredicate.getComparisonOperator());
    LiteralExpression orLiteralExpression = (LiteralExpression) orComparisonPredicate.getRightHandOperand();
    assertNotNull(orLiteralExpression);
    assertEquals(orValue, orLiteralExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Different() {
    TestObject value = new TestObject("different");
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
  void buildBaseFilterPredicate_Or_Is_Different() {
    TestObject value = new TestObject("different");
    Mockito.when(path.getJavaType()).thenReturn(String.class);
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsDifferent(value);
    TestFilter testFilter = new TestFilter();
    testFilter.setOr(orTestFilter);
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
    TestObject orValue = new TestObject("orDifferent");
    Mockito.when(path.getJavaType()).thenReturn(String.class);
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsDifferent(orValue);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsDifferent(andValue);
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());

    ComparisonPredicate andComparisonPredicate = (ComparisonPredicate) expressions.get(0);
    assertNotNull(andComparisonPredicate);
    assertEquals(ComparisonOperator.NOT_EQUAL, andComparisonPredicate.getComparisonOperator());
    LiteralExpression andLiteralExpression = (LiteralExpression) andComparisonPredicate.getRightHandOperand();
    assertNotNull(andLiteralExpression);
    assertEquals(andValue, andLiteralExpression.getLiteral());

    ComparisonPredicate orComparisonPredicate = (ComparisonPredicate) expressions.get(1);
    assertNotNull(orComparisonPredicate);
    assertEquals(ComparisonOperator.NOT_EQUAL, orComparisonPredicate.getComparisonOperator());
    LiteralExpression orLiteralExpression = (LiteralExpression) orComparisonPredicate.getRightHandOperand();
    assertNotNull(orLiteralExpression);
    assertEquals(orValue, orLiteralExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_True_() {
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
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
  void buildBaseFilterPredicate_Or_Is_Null_True() {
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setNull(true);
    TestFilter testFilter = new TestFilter();
    testFilter.setOr(orTestFilter);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_Null_True() {
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setNull(true);
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(true);
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

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
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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
  void buildBaseFilterPredicate_Or_Is_Null_False() {
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setNull(false);
    TestFilter testFilter = new TestFilter();
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_Null_False() {
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setNull(false);
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(false);
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());
    assertTrue(expressions.stream().allMatch(expression -> expression instanceof NegatedPredicateWrapper));
  }

  @Test
  void buildBaseFilterPredicate_Is_In() {
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsIn(values);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    InPredicate inPredicate = (InPredicate) predicate;

    List<LiteralExpression<TestObject>> predicateValues = inPredicate.getValues();
    assertEquals(values.size(), predicateValues.size());
    List<TestObject> inValues = predicateValues.stream().map(LiteralExpression::getLiteral).collect(
            Collectors.toList());
    assertLinesMatch(values.stream().map(TestObject::getName).collect(Collectors.toList()), inValues.stream()
            .map(TestObject::getName).collect(Collectors.toList()));
  }

  @Test
  void buildBaseFilterPredicate_Is_In_With_Not_Null_Predicate() {
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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

    InPredicate inPredicate = (InPredicate) expressions.get(1);

    List<LiteralExpression<TestObject>> predicateValues = inPredicate.getValues();
    assertEquals(values.size(), predicateValues.size());
    List<TestObject> inValues = predicateValues.stream().map(LiteralExpression::getLiteral).collect(
            Collectors.toList());
    assertLinesMatch(values.stream().map(TestObject::getName).collect(Collectors.toList()), inValues.stream()
            .map(TestObject::getName).collect(Collectors.toList()));
  }

  @Test
  void buildBaseFilterPredicate_Or_Is_In() {
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsIn(values);
    TestFilter testFilter = new TestFilter();
    testFilter.setOr(orTestFilter);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    InPredicate inPredicate = (InPredicate) predicate;

    List<LiteralExpression<TestObject>> predicateValues = inPredicate.getValues();
    assertEquals(values.size(), predicateValues.size());
    List<TestObject> inValues = predicateValues.stream().map(LiteralExpression::getLiteral).collect(
            Collectors.toList());
    assertLinesMatch(values.stream().map(TestObject::getName).collect(Collectors.toList()), inValues.stream()
            .map(TestObject::getName).collect(Collectors.toList()));
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_In() {
    List<TestObject> andValues = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    List<TestObject> orValues = Arrays.asList(new TestObject("def"), new TestObject("lmn"));
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsIn(orValues);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsIn(andValues);
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());

    InPredicate<TestObject> andInPredicate = (InPredicate) expressions.get(0);

    List<Expression<? extends TestObject>> andInPredicateValues = andInPredicate.getValues();
    assertEquals(andValues.size(), andInPredicateValues.size());
    List<TestObject> andInValues = andInPredicateValues.stream().map(expression -> (LiteralExpression<TestObject>) expression).map(LiteralExpression::getLiteral).collect(
            Collectors.toList());
    assertLinesMatch(andValues.stream().map(TestObject::getName).collect(Collectors.toList()), andInValues.stream()
            .map(TestObject::getName).collect(Collectors.toList()));

    InPredicate<TestObject> orInPredicate = (InPredicate) expressions.get(1);

    List<Expression<? extends TestObject>> orInPredicateValues = orInPredicate.getValues();
    assertEquals(orValues.size(), orInPredicateValues.size());
    List<TestObject> orInValues = orInPredicateValues.stream().map(expression -> (LiteralExpression<TestObject>) expression).map(LiteralExpression::getLiteral).collect(
            Collectors.toList());
    assertLinesMatch(orValues.stream().map(TestObject::getName).collect(Collectors.toList()), orInValues.stream()
            .map(TestObject::getName).collect(Collectors.toList()));
  }

  @Test
  void buildBaseFilterPredicate_Is_Not_In() {
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter testFilter = new TestFilter();
    testFilter.setIsNotIn(values);
    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_Is_Not_In_With_Not_Null_Predicate() {
    Mockito.when(path.getJavaType()).thenReturn(String.class);
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

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) expressions.get(1);
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_Or_Is_Not_In() {
    List<TestObject> values = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsNotIn(values);
    TestFilter testFilter = new TestFilter();
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_And_With_Or_Is_Not_In() {
    List<TestObject> andValues = Arrays.asList(new TestObject("abc"), new TestObject("xyz"));
    List<TestObject> orValues = Arrays.asList(new TestObject("def"), new TestObject("lmn"));
    TestFilter orTestFilter = new TestFilter();
    orTestFilter.setIsNotIn(orValues);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsNotIn(andValues);
    testFilter.setOr(orTestFilter);

    Predicate predicate = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
            FIELD_NAME);

    assertNotNull(predicate);

    CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
    assertEquals(OR, compoundPredicate.getOperator().name());

    List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
    assertNotNull(expressions);
    assertEquals(2, expressions.size());

    assertTrue(expressions.stream().allMatch(expression -> expression instanceof NegatedPredicateWrapper));
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