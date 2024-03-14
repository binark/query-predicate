package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.NumericFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class NumericFilterBetweenPredicateBuilderTest {

    public static final String FIELD_NAME = "fieldName";
    public static final int VALUE = 123;
    public static final int OTHER_VALUE = 456;
    public static final String AND = "AND";
    public static final String OR = "OR";

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
        //  Mockito.when(path.getJavaType()).thenReturn(Number.class);
        filter = mock(NumericFilter.class, CALLS_REAL_METHODS);
    }

    @Test
    void buildNumericPredicate_Is_Between() {
        Range<Integer> range = new Range<>();
        range.setStart(VALUE);
        range.setEnd(OTHER_VALUE);
        filter.setIsBetween(range);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate<Integer> betweenPredicate = (BetweenPredicate) predicate;
        LiteralExpression<Integer> lowerBound = (LiteralExpression<Integer>) betweenPredicate.getLowerBound();

        assertNotNull(lowerBound);
        assertEquals(VALUE, lowerBound.getLiteral());

        LiteralExpression<Integer> upperBound = (LiteralExpression<Integer>) betweenPredicate.getUpperBound();
        assertNotNull(upperBound);
        assertEquals(OTHER_VALUE, upperBound.getLiteral());
    }

    @Test
    void buildNumericPredicate_Is_Between_With_Not_Null_Predicate() {
        Range<Integer> range = new Range<>();
        range.setStart(VALUE);
        range.setEnd(OTHER_VALUE);
        filter.setNull(false);
        filter.setIsBetween(range);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate<Integer> betweenPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Integer> lowerBound = (LiteralExpression<Integer>) betweenPredicate.getLowerBound();

        assertNotNull(lowerBound);
        assertEquals(VALUE, lowerBound.getLiteral());

        LiteralExpression<Integer> upperBound = (LiteralExpression<Integer>) betweenPredicate.getUpperBound();
        assertNotNull(upperBound);
        assertEquals(OTHER_VALUE, upperBound.getLiteral());
    }

    @Test
    void buildNumericPredicate_Or_Is_Between() {
        NumericFilter orFilter = mock(NumericFilter.class, CALLS_REAL_METHODS);
        Range<Integer> range = new Range<>();
        range.setStart(VALUE);
        range.setEnd(OTHER_VALUE);
        orFilter.setIsBetween(range);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate<Integer> betweenPredicate = (BetweenPredicate) predicate;
        LiteralExpression<Integer> lowerBound = (LiteralExpression<Integer>) betweenPredicate.getLowerBound();

        assertNotNull(lowerBound);
        assertEquals(VALUE, lowerBound.getLiteral());

        LiteralExpression<Integer> upperBound = (LiteralExpression<Integer>) betweenPredicate.getUpperBound();
        assertNotNull(upperBound);
        assertEquals(OTHER_VALUE, upperBound.getLiteral());
    }

    @Test
    void buildNumericPredicate_And_With_Or_Is_Between() {
        NumericFilter orFilter = mock(NumericFilter.class, CALLS_REAL_METHODS);
        Range<Integer> orRange = new Range<>();
        orRange.setStart(VALUE);
        orRange.setEnd(OTHER_VALUE);
        orFilter.setIsBetween(orRange);
        Range<Integer> andRange = new Range<>();
        andRange.setStart(OTHER_VALUE);
        andRange.setEnd(VALUE);
        filter.setIsBetween(andRange);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate<Integer> andBetweenPredicate = (BetweenPredicate<Integer>) expressions.get(0);
        LiteralExpression<Integer> andLowerBound = (LiteralExpression<Integer>) andBetweenPredicate.getLowerBound();

        assertNotNull(andLowerBound);
        assertEquals(OTHER_VALUE, andLowerBound.getLiteral());

        LiteralExpression<Integer> andUpperBound = (LiteralExpression<Integer>) andBetweenPredicate.getUpperBound();
        assertNotNull(andUpperBound);
        assertEquals(VALUE, andUpperBound.getLiteral());

        BetweenPredicate<Integer> orBetweenPredicate = (BetweenPredicate<Integer>) expressions.get(1);
        LiteralExpression<Integer> orLowerBound = (LiteralExpression<Integer>) orBetweenPredicate.getLowerBound();

        assertNotNull(orLowerBound);
        assertEquals(VALUE, orLowerBound.getLiteral());

        LiteralExpression<Integer> orUpperBound = (LiteralExpression<Integer>) orBetweenPredicate.getUpperBound();
        assertNotNull(orUpperBound);
        assertEquals(OTHER_VALUE, orUpperBound.getLiteral());
    }
}