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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstantFilterTomorrowPredicateBuilderTest extends InstantFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "tomorrow";

    @Test
    void buildTomorrowPredicate() {
        InstantFilter localDateFilter = new InstantFilter();
        localDateFilter.setIsTomorrow(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<Instant> lowerBound = (LiteralExpression<Instant>) betweenPredicate.getLowerBound();
        LiteralExpression<Instant> upperBound = (LiteralExpression<Instant>) betweenPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), lowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), upperBound.getLiteral());
    }

    @Test
    void buildAndTomorrowPredicate() {
        BaseInstantFilter andFilter = new BaseInstantFilter();
        andFilter.setIsTomorrow(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsTomorrow(true);
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

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), andLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTomorrowPredicate() {
        BaseInstantFilter orFilter = new BaseInstantFilter();
        orFilter.setIsTomorrow(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsTomorrow(true);
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

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), orLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTomorrowPredicate() {
        BaseInstantFilter andFilter = new BaseInstantFilter();
        andFilter.setIsTomorrow(true);
        BaseInstantFilter orFilter = new BaseInstantFilter();
        orFilter.setIsTomorrow(true);
        InstantFilter filter = new InstantFilter();
        filter.setIsTomorrow(true);
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

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), orLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Instant> andLowerBound = (LiteralExpression<Instant>) andTodayPredicate.getLowerBound();
        LiteralExpression<Instant> andUpperBound = (LiteralExpression<Instant>) andTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), andLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Instant> isLowerBound = (LiteralExpression<Instant>) isTodayPredicate.getLowerBound();
        LiteralExpression<Instant> isUpperBound = (LiteralExpression<Instant>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isLowerBound.getLiteral());
        assertEquals(getEndOfDay(Instant.now().plus(1, ChronoUnit.DAYS)), isUpperBound.getLiteral());
    }
}