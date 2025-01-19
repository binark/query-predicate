package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDateFilter;
import io.github.binark.querypredicate.filter.DateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateFilterTodayPredicateBuilderTest extends DateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "today";

    @Test
    void buildTodayPredicate() {
        DateFilter filter = new DateFilter();
        filter.setIsToday(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
    }

    @Test
    void buildAndTodayPredicate() {
        BaseDateFilter andFilter = new BaseDateFilter();
        andFilter.setIsToday(true);
        DateFilter filter = new DateFilter();
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
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), andLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), isLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTodayPredicate() {
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsToday(true);
        DateFilter filter = new DateFilter();
        filter.setIsToday(true);
        filter.setOr(orFilter);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate comparisonPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, comparisonPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = comparisonPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(0));
        BetweenPredicate orPredicate = (BetweenPredicate) expressions.get(0);
        LiteralExpression<Date> orLowerBound = (LiteralExpression<Date>) orPredicate.getLowerBound();
        LiteralExpression<Date> orUpperBound = (LiteralExpression<Date>) orPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), orLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), isLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseDateFilter andrFilter = new BaseDateFilter();
        andrFilter.setIsToday(true);
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsToday(true);
        DateFilter filter = new DateFilter();
        filter.setIsToday(true);
        filter.setAnd(andrFilter);
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
        LiteralExpression<Date> orLowerBound = (LiteralExpression<Date>) orPredicate.getLowerBound();
        LiteralExpression<Date> orUpperBound = (LiteralExpression<Date>) orPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), orLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(0));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(0);
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andTodayPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andTodayPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), andLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), isLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), isUpperBound.getLiteral());
    }
}