package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.Range;
import io.github.binark.querypredicate.testdouble.TestBaseComparableFilter;
import io.github.binark.querypredicate.testdouble.TestComparableFilter;
import io.github.binark.querypredicate.testdouble.TestComparableObject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
public class ComparableBetweenPredicateBuilderTest {

    public static final String FIELD_NAME = "fieldName";
    private final TestComparableObject betweenStart = new TestComparableObject();
    private final TestComparableObject betweenEnd = new TestComparableObject();
    @Mock(answer = Answers.RETURNS_SELF)
    private Path path;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;
    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ComparableFilterPredicateBuilder<TestComparableFilter> predicateBuilder;
    @Mock
    private Predicate basePredicate;

    @Test
    void testBuildIsBetweenPredicate() {
        Range<TestComparableObject> between = new Range<>();
        between.setStart(betweenStart);
        between.setEnd(betweenEnd);
        TestComparableFilter filter = new TestComparableFilter();
        filter.setIsBetween(between);

        Predicate predicate = predicateBuilder.addBetweenPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                   FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression lowerBound = (LiteralExpression) betweenPredicate.getLowerBound();
        assertEquals(betweenStart, lowerBound.getLiteral());
        LiteralExpression upperBound = (LiteralExpression) betweenPredicate.getUpperBound();
        assertEquals(betweenEnd, upperBound.getLiteral());
    }

    @Test
    void testBuildAndBetweenPredicate() {
        Range<TestComparableObject> between = new Range<>();
        between.setStart(betweenStart);
        between.setEnd(betweenEnd);
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter andFilter = new TestBaseComparableFilter();
        andFilter.setIsBetween(between);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.addBetweenPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                   FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression lowerBound = (LiteralExpression) betweenPredicate.getLowerBound();
        assertEquals(betweenStart, lowerBound.getLiteral());
        LiteralExpression upperBound = (LiteralExpression) betweenPredicate.getUpperBound();
        assertEquals(betweenEnd, upperBound.getLiteral());
    }

    @Test
    void testBuildOrBetweenPredicate() {
        Range<TestComparableObject> between = new Range<>();
        between.setStart(betweenStart);
        between.setEnd(betweenEnd);
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter orFilter = new TestBaseComparableFilter();
        orFilter.setIsBetween(between);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.addBetweenPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                   FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression lowerBound = (LiteralExpression) betweenPredicate.getLowerBound();
        assertEquals(betweenStart, lowerBound.getLiteral());
        LiteralExpression upperBound = (LiteralExpression) betweenPredicate.getUpperBound();
        assertEquals(betweenEnd, upperBound.getLiteral());
    }

    @Test
    void testBuildFullLessThanPredicate() {
        Range<TestComparableObject> between = new Range<>();
        between.setStart(betweenStart);
        between.setEnd(betweenEnd);
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter orFilter = new TestBaseComparableFilter();
        orFilter.setIsBetween(between);
        filter.setOr(orFilter);
        TestBaseComparableFilter andFilter = new TestBaseComparableFilter();
        andFilter.setIsBetween(between);
        filter.setAnd(andFilter);
        filter.setIsBetween(between);

        Predicate predicate = predicateBuilder.addBetweenPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                   FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andPredicate.getOperator());
        List<Expression<Boolean>> andPredicateExpressions = andPredicate.getExpressions();
        assertInstanceOf(CompoundPredicate.class, andPredicateExpressions.get(0));
        CompoundPredicate isPredicate = (CompoundPredicate) andPredicateExpressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, isPredicate.getOperator());
        List<Expression<Boolean>> isPredicateExpressions = isPredicate.getExpressions();
        assertEquals(basePredicate, isPredicateExpressions.get(0));
        assertInstanceOf(BetweenPredicate.class, isPredicateExpressions.get(1));
        BetweenPredicate isBetweenPredicate = (BetweenPredicate) isPredicateExpressions.get(1);
        LiteralExpression isBetweenPredicateLowerBound = (LiteralExpression) isBetweenPredicate.getLowerBound();
        assertEquals(betweenStart, isBetweenPredicateLowerBound.getLiteral());
        LiteralExpression isBetweenPredicateUpperBound = (LiteralExpression) isBetweenPredicate.getUpperBound();
        assertEquals(betweenEnd, isBetweenPredicateUpperBound.getLiteral());
        assertInstanceOf(BetweenPredicate.class, andPredicateExpressions.get(1));
        BetweenPredicate andBetweenPredicate = (BetweenPredicate) andPredicateExpressions.get(1);
        LiteralExpression andBetweenPredicateLowerBound = (LiteralExpression) andBetweenPredicate.getLowerBound();
        assertEquals(betweenStart, andBetweenPredicateLowerBound.getLiteral());
        LiteralExpression andBetweenPredicateUpperBound = (LiteralExpression) andBetweenPredicate.getUpperBound();
        assertEquals(betweenEnd, andBetweenPredicateUpperBound.getLiteral());
        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate orPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression lowerBound = (LiteralExpression) orPredicate.getLowerBound();
        assertEquals(betweenStart, lowerBound.getLiteral());
        LiteralExpression upperBound = (LiteralExpression) orPredicate.getUpperBound();
        assertEquals(betweenEnd, upperBound.getLiteral());
    }
}
