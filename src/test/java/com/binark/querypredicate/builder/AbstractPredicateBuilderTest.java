package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.binark.querypredicate.annotation.EntityFieldName;
import com.binark.querypredicate.utils.TestFilter;
import com.binark.querypredicate.utils.TestQueryDescriptor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.hibernate.query.criteria.internal.predicate.InPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.hibernate.query.criteria.internal.predicate.NullnessPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractPredicateBuilderTest {

  public static final String FIELD_NAME = "fieldName";

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private AbstractPredicateBuilder predicateBuilder;

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  @Test
  void buildBaseFilterPredicate_Is_Equals() {
    String value = "equals";
    Mockito.when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsEquals(value);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Different() {
    String value = "different";
    Mockito.when(path.getJavaType()).thenReturn(String.class);
    TestFilter testFilter = new TestFilter();
    testFilter.setIsDifferent(value);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertNotNull(literalExpression);
    assertEquals(value, literalExpression.getLiteral());
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_True() {
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(true);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    NullnessPredicate nullnessPredicate = (NullnessPredicate) predicates.get(0);
    assertNotNull(nullnessPredicate);
  }

  @Test
  void buildBaseFilterPredicate_Is_Null_False() {
    TestFilter testFilter = new TestFilter();
    testFilter.setNull(false);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicates.get(0);
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildBaseFilterPredicate_Is_In() {
    List<String> values = Arrays.asList("abc", "xyz");
    TestFilter testFilter = new TestFilter();
    testFilter.setIsIn(values);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    InPredicate inPredicate = (InPredicate) predicates.get(0);

    List<LiteralExpression<String>> predicateValues = inPredicate.getValues();
    assertEquals(values.size(), predicateValues.size());
    List<String> inValues = predicateValues.stream().map(LiteralExpression::getLiteral).toList();
    assertLinesMatch(values, inValues);
  }

  @Test
  void buildBaseFilterPredicate_Is_Not_In() {
    List<String> values = Arrays.asList("abc", "xyz");
    TestFilter testFilter = new TestFilter();
    testFilter.setIsNotIn(values);
    List<Predicate> predicates = predicateBuilder.buildBaseFilterPredicate(path, criteriaBuilder, testFilter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicates.get(0);
    assertNotNull(negatedPredicateWrapper);
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