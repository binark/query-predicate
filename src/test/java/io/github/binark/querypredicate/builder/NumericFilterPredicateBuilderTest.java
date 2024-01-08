package io.github.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.binark.querypredicate.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NumericFilterPredicateBuilderTest {

  public static final String FIELD_NAME = "fieldName";
  public static final int VALUE = 123;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private NumericFilterPredicateBuilder predicateBuilder;

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  @InjectMocks
  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  private NumericFilter filter;

  @BeforeEach
  void setUp() {
    Mockito.when(path.getJavaType()).thenReturn(Number.class);
    filter = Mockito.mock(NumericFilter.class, Mockito.CALLS_REAL_METHODS);
  }

  @Test
  void buildNumericPredicate_Is_GreaterThan() {
    filter.setIsGreaterThan(VALUE);

    List<Predicate> predicates = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
        filter, FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildNumericPredicate_Is_GreaterThan_Or_Equals_To() {
    filter.setIsGreaterThanOrEqualsTo(VALUE);

    List<Predicate> predicates = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
        filter, FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildNumericPredicate_Is_LessThan() {
    filter.setIsLessThan(VALUE);

    List<Predicate> predicates = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
        filter, FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildNumericPredicate_Is_LessThan_Or_Equals_To() {
    filter.setIsLessThanOrEqualsTo(VALUE);

    List<Predicate> predicates = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
        filter, FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }
}