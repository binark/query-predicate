package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseInstantFilter;
import io.github.binark.querypredicate.filter.InstantFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstantFilterTodayPredicateBuilderTest extends InstantFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "today";

    @Test
    void buildTodayPredicate() {
        InstantFilter localDateFilter = new InstantFilter();
        localDateFilter.setIsToday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<Instant> lowerBound = (LiteralExpression<Instant>) betweenPredicate.getLowerBound();
        LiteralExpression<Instant> upperBound = (LiteralExpression<Instant>) betweenPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), lowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), upperBound.getLiteral());
    }

    @Test
    void buildAndTodayPredicate() {
        BaseInstantFilter andFilter = new BaseInstantFilter();
        andFilter.setIsToday(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsToday(true);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(0));
        BetweenPredicate andPredicate = (BetweenPredicate) expressions.get(0);
        LiteralExpression<Instant> andLowerBound = (LiteralExpression<Instant>) andPredicate.getLowerBound();
        LiteralExpression<Instant> andUpperBound = (LiteralExpression<Instant>) andPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), andLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTodayPredicate() {
        BaseInstantFilter orFilter = new BaseInstantFilter();
        orFilter.setIsToday(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsToday(true);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(0));
        BetweenPredicate orPredicate = (BetweenPredicate) expressions.get(0);
        LiteralExpression<Instant> orLowerBound = (LiteralExpression<Instant>) orPredicate.getLowerBound();
        LiteralExpression<Instant> orUpperBound = (LiteralExpression<Instant>) orPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), orLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseInstantFilter andFilter = new BaseInstantFilter();
        andFilter.setIsToday(true);
        BaseInstantFilter orFilter = new BaseInstantFilter();
        orFilter.setIsToday(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsToday(true);
        filter.setAnd(andFilter);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate orPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Instant> orLowerBound = (LiteralExpression<Instant>) orPredicate.getLowerBound();
        LiteralExpression<Instant> orUpperBound = (LiteralExpression<Instant>) orPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), orLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Instant> andLowerBound = (LiteralExpression<Instant>) andTodayPredicate.getLowerBound();
        LiteralExpression<Instant> andUpperBound = (LiteralExpression<Instant>) andTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), andLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now()), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now()), isUpperBound.getLiteral());
    }
}