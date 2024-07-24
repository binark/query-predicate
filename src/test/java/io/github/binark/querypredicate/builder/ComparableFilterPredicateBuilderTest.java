package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.ComparableFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComparableFilterPredicateBuilderTest {

  public static final String FIELD_NAME = "fieldName";
  public static final Instant VALUE = Instant.now();
    public static final Instant OTHER_VALUE = Instant.MIN;
    public static final String AND = "AND";
    public static final String OR = "OR";

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

      Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

      assertNotNull(predicate);
      ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;

    assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

    @Test
    void buildComparablePredicate_Is_GreaterThan_With_Not_Null_Predicate() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsGreaterThan(VALUE);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate equalsPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.EQUAL, equalsPredicate.getComparisonOperator());

        LiteralExpression equalsLiteralExpression = (LiteralExpression) equalsPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, equalsLiteralExpression.getLiteral());

        ComparisonPredicate greaterThanPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.GREATER_THAN, greaterThanPredicate.getComparisonOperator());

        LiteralExpression greaterThanLiteralExpression = (LiteralExpression) greaterThanPredicate.getRightHandOperand();
        assertEquals(VALUE, greaterThanLiteralExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_Or_Is_GreaterThan() {
        ComparableFilter orFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        orFilter.setIsGreaterThan(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;

        assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_And_With_Or_Is_GreaterThan() {
        ComparableFilter andFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        andFilter.setIsGreaterThan(VALUE);
        filter.setIsGreaterThan(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate andGreaterThanPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.GREATER_THAN, andGreaterThanPredicate.getComparisonOperator());

        LiteralExpression andLiteralExpression = (LiteralExpression) andGreaterThanPredicate.getRightHandOperand();
        assertEquals(VALUE, andLiteralExpression.getLiteral());

        ComparisonPredicate orGreaterThanPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.GREATER_THAN, orGreaterThanPredicate.getComparisonOperator());

        LiteralExpression orLiteralExpression = (LiteralExpression) orGreaterThanPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, orLiteralExpression.getLiteral());
    }

  @Test
  void buildComparablePredicate_Is_GreaterThan_Or_Equals_To() {
    filter.setIsGreaterThanOrEqualsTo(VALUE);

      Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

      assertNotNull(predicate);
      assertInstanceOf(ComparisonPredicate.class, predicate);

      ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

    @Test
    void buildComparablePredicate_Is_GreaterThan_Or_Equals_To_With_Not_Null_Predicate() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsGreaterThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_Or_Is_GreaterThan_Or_Equals_To() {
        ComparableFilter orFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        orFilter.setIsGreaterThanOrEqualsTo(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_And_With_Or_Is_GreaterThan_Or_Equals_To() {
        ComparableFilter andFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        andFilter.setIsGreaterThanOrEqualsTo(VALUE);
        filter.setIsGreaterThanOrEqualsTo(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate andGreaterThanOrEqualsToPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, andGreaterThanOrEqualsToPredicate.getComparisonOperator());

        LiteralExpression andLiteralExpression = (LiteralExpression) andGreaterThanOrEqualsToPredicate.getRightHandOperand();
        assertEquals(VALUE, andLiteralExpression.getLiteral());

        ComparisonPredicate orGreaterThanOrEqualsToPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, orGreaterThanOrEqualsToPredicate.getComparisonOperator());

        LiteralExpression orLiteralExpression = (LiteralExpression) orGreaterThanOrEqualsToPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, orLiteralExpression.getLiteral());
    }

  @Test
  void buildComparablePredicate_Is_LessThan() {
    filter.setIsLessThan(VALUE);

      Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

      assertNotNull(predicate);
      assertInstanceOf(ComparisonPredicate.class, predicate);

      ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

    @Test
    void buildComparablePredicate_Is_LessThan_With_Not_Null_Predicate() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsLessThan(VALUE);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_Or_Is_LessThan() {
        ComparableFilter orFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        orFilter.setIsLessThan(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_And_With_Or_Is_LessThan() {
        ComparableFilter andFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        andFilter.setIsLessThan(VALUE);
        filter.setIsLessThan(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate andLessThanPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.LESS_THAN, andLessThanPredicate.getComparisonOperator());

        LiteralExpression andLiteralExpression = (LiteralExpression) andLessThanPredicate.getRightHandOperand();
        assertEquals(VALUE, andLiteralExpression.getLiteral());

        ComparisonPredicate orLessThanPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN, orLessThanPredicate.getComparisonOperator());

        LiteralExpression orLiteralExpression = (LiteralExpression) orLessThanPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, orLiteralExpression.getLiteral());
    }

  @Test
  void buildComparablePredicate_Is_LessThan_Or_Equals_To() {
    filter.setIsLessThanOrEqualsTo(VALUE);

      Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
              FIELD_NAME);

      assertNotNull(predicate);
      assertInstanceOf(ComparisonPredicate.class, predicate);

      ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
      assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

      LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
      assertEquals(VALUE, literalExpression.getLiteral());
  }

    @Test
    void buildComparablePredicate_Is_LessThan_Or_Equals_To_With_Not_Null_Predicate() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsLessThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildComparablePredicate_Or_Is_LessThan_Or_Equals_To() {
        ComparableFilter orFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        orFilter.setIsLessThanOrEqualsTo(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
    assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

    LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
    assertEquals(VALUE, literalExpression.getLiteral());
  }

    @Test
    void buildComparablePredicate_And_With_Or_Is_LessThan_Or_Equals_To() {
        ComparableFilter andFilter = Mockito.mock(ComparableFilter.class, Mockito.CALLS_REAL_METHODS);
        andFilter.setIsLessThanOrEqualsTo(VALUE);
        filter.setIsLessThanOrEqualsTo(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildComparablePredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate andLessThanOrEqualsToPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, andLessThanOrEqualsToPredicate.getComparisonOperator());

        LiteralExpression andLiteralExpression = (LiteralExpression) andLessThanOrEqualsToPredicate.getRightHandOperand();
        assertEquals(VALUE, andLiteralExpression.getLiteral());

        ComparisonPredicate orLessThanOrEqualsToPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, orLessThanOrEqualsToPredicate.getComparisonOperator());

        LiteralExpression orLiteralExpression = (LiteralExpression) orLessThanOrEqualsToPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, orLiteralExpression.getLiteral());
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