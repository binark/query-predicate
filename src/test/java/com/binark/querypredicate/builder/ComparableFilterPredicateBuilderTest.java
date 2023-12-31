package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.binark.querypredicate.filter.ComparableFilter;
import com.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.List;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComparableFilterPredicateBuilderTest {

  public static final String FIELD_NAME = "fieldName";
  public static final Instant VALUE = Instant.now();

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private ComparableFilterPredicateBuilder predicateBuilder;

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  private ComparableFilter filter;

  @BeforeEach
  void setUp() {
    Mockito.lenient().when(path.getJavaType()).thenReturn(Instant.class);
    filter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
  }

  @Test
  void buildComparablePredicate_Is_GreaterThan() {
    filter.setIsGreaterThan(VALUE);

    List<Predicate> predicates = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildComparablePredicate_Is_GreaterThan_Or_Equals_To() {
    filter.setIsGreaterThanOrEqualsTo(VALUE);

    List<Predicate> predicates = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildComparablePredicate_Is_LessThan() {
    filter.setIsLessThan(VALUE);

    List<Predicate> predicates = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void buildComparablePredicate_Is_LessThan_Or_Equals_To() {
    filter.setIsLessThanOrEqualsTo(VALUE);

    List<Predicate> predicates = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicates);
    assertEquals(1, predicates.size());
    assertInstanceOf(ComparisonPredicate.class, predicates.get(0));

    ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.get(0);
    assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

  @Test
  void getBetweenPredicate() {
    Instant start = Instant.MIN;
    Instant end = Instant.now();
    Range<Instant> range = new Range<>();
    range.setStart(start);
    range.setEnd(end);

    Predicate predicate = predicateBuilder.getBetweenPredicate(path, criteriaBuilder, range,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(BetweenPredicate.class, predicate);
    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

    LiteralExpression lowerBound = (LiteralExpression) betweenPredicate.getLowerBound();
    LiteralExpression upperBound = (LiteralExpression) betweenPredicate.getUpperBound();

    assertEquals(start, lowerBound.getLiteral());
    assertEquals(end, upperBound.getLiteral());
  }

  @Test
  void getBetweenPredicate_Without_Start() {
    Range<Instant> range = new Range<>();
    range.setEnd(Instant.now());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> predicateBuilder.getBetweenPredicate(path, criteriaBuilder, range,
            FIELD_NAME));

    assertTrue(illegalArgumentException.getMessage().contains("start and end"));
  }

  @Test
  void getBetweenPredicate_Without_End() {
    Range<Instant> range = new Range<>();
    range.setStart(Instant.now());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> predicateBuilder.getBetweenPredicate(path, criteriaBuilder, range,
            FIELD_NAME));

    assertTrue(illegalArgumentException.getMessage().contains("start and end"));
  }
}