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

class DateFilterTomorrowPredicateBuilderTest extends DateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "tomorrow";

    @Test
    void buildTomorrowPredicate() {
        DateFilter filter = new DateFilter();
        filter.setIsTomorrow(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), lowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), upperBound.getLiteral());
    }

    @Test
    void buildAndTomorrowPredicate() {
        BaseDateFilter andFilter = new BaseDateFilter();
        andFilter.setIsTomorrow(true);
        DateFilter filter = new DateFilter();
        filter.setIsTomorrow(true);
        filter.setAnd(andFilter);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate comparisonPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, comparisonPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = comparisonPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(0));
        BetweenPredicate andPredicate = (BetweenPredicate) expressions.get(0);
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), andLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), isLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTomorrowPredicate() {
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsTomorrow(true);
        DateFilter filter = new DateFilter();
        filter.setIsTomorrow(true);
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

        assertEquals(getStartOfTomorrow(), orLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), isLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTomorrowPredicate() {
        BaseDateFilter andrFilter = new BaseDateFilter();
        andrFilter.setIsTomorrow(true);
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsTomorrow(true);
        DateFilter filter = new DateFilter();
        filter.setIsTomorrow(true);
        filter.setAnd(andrFilter);
        filter.setOr(orFilter);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate comparisonPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, comparisonPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = comparisonPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate orPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> orLowerBound = (LiteralExpression<Date>) orPredicate.getLowerBound();
        LiteralExpression<Date> orUpperBound = (LiteralExpression<Date>) orPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), orLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate compoundPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = compoundPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(0));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(0);
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andTodayPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andTodayPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), andLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfTomorrow(), isLowerBound.getLiteral());
        assertEquals(getEndOfTomorrow(), isUpperBound.getLiteral());
    }
}