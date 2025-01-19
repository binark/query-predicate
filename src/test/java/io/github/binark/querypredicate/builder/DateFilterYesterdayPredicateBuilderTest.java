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

class DateFilterYesterdayPredicateBuilderTest extends DateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "yesterday";

    @Test
    void buildYesterdayPredicate() {
        DateFilter filter = new DateFilter();
        filter.setIsYesterday(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(getStartOfYesterday(), lowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), upperBound.getLiteral());
    }

    @Test
    void buildAndYesterdayPredicate() {
        BaseDateFilter andFilter = new BaseDateFilter();
        andFilter.setIsYesterday(true);
        DateFilter filter = new DateFilter();
        filter.setIsYesterday(true);
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

        assertEquals(getStartOfYesterday(), andLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfYesterday(), isLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), isUpperBound.getLiteral());
    }

    @Test
    void buildOrYesterdayPredicate() {
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsYesterday(true);
        DateFilter filter = new DateFilter();
        filter.setIsYesterday(true);
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

        assertEquals(getStartOfYesterday(), orLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfYesterday(), isLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), isUpperBound.getLiteral());
    }

    @Test
    void buildFullYesterdayPredicate() {
        BaseDateFilter andrFilter = new BaseDateFilter();
        andrFilter.setIsYesterday(true);
        BaseDateFilter orFilter = new BaseDateFilter();
        orFilter.setIsYesterday(true);
        DateFilter filter = new DateFilter();
        filter.setIsYesterday(true);
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

        assertEquals(getStartOfYesterday(), orLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate compoundPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = compoundPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(0));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(0);
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andTodayPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andTodayPredicate.getUpperBound();

        assertEquals(getStartOfYesterday(), andLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<Date> isLowerBound = (LiteralExpression<Date>) isTodayPredicate.getLowerBound();
        LiteralExpression<Date> isUpperBound = (LiteralExpression<Date>) isTodayPredicate.getUpperBound();

        assertEquals(getStartOfYesterday(), isLowerBound.getLiteral());
        assertEquals(getEndOfYesterday(), isUpperBound.getLiteral());
    }
}