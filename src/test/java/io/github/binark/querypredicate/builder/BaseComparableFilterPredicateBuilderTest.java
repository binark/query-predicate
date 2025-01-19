package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseComparableFilter;
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
class BaseComparableFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "fieldName";
    public static final Instant VALUE = Instant.now();
    public static final Instant OTHER_VALUE = Instant.MIN;
    public static final String AND = "AND";
    public static final String OR = "OR";

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private BaseComparableFilterPredicateBuilder predicateBuilder;

    @Mock(answer = Answers.RETURNS_SELF)
    private Path path;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;

    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    private BaseComparableFilter filter;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(path.getJavaType()).thenReturn(Instant.class);
        filter = Mockito.mock(BaseComparableFilter.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void buildIsGreaterThanPredicate() {
        filter.setIsGreaterThan(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;

        assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildCombinedIsEqualsAndIsGreaterThanPredicates() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsGreaterThan(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

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
    void buildIsGreaterThanOrEqualsToPredicate() {
        filter.setIsGreaterThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildCombinedIsEqualsAndIsGreaterThanOrEqualsToPredicate() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsGreaterThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildIsLessThanPredicate() {
        filter.setIsLessThan(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildCombinedIsEqualsAndIsLessThanPredicates() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsLessThan(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildIsLessThanOrEqualsToPredicate() {
        filter.setIsLessThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildCombinedIsEqualsAnsIsLessThanOrEqualsToPredicates() {
        filter.setIsEquals(OTHER_VALUE);
        filter.setIsLessThanOrEqualsTo(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void testBetweenPredicate() {
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
    void testBetweenPredicateWithoutStart() {
        Range<Instant> range = new Range<>();
        range.setEnd(Instant.now());

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                                                                         () -> predicateBuilder.getBetweenPredicate(path, criteriaBuilder, range,
                                                                                                                    FIELD_NAME));

        assertTrue(illegalArgumentException.getMessage().contains("start and end"));
    }

    @Test
    void testBetweenPredicateWithoutEnd() {
        Range<Instant> range = new Range<>();
        range.setStart(Instant.now());

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                                                                         () -> predicateBuilder.getBetweenPredicate(path, criteriaBuilder, range,
                                                                                                                    FIELD_NAME));

        assertTrue(illegalArgumentException.getMessage().contains("start and end"));
    }
}