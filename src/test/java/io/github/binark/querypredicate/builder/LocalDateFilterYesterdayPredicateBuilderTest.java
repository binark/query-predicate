package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseLocalDateFilter;
import io.github.binark.querypredicate.filter.LocalDateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateFilterYesterdayPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "yesterday";

    @Test
    void buildYesterdayPredicate() {
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsYesterday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildAndYesterdayPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsYesterday(true);
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsYesterday(true);
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
        LiteralExpression<LocalDate> andLowerBound = (LiteralExpression<LocalDate>) andPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound = (LiteralExpression<LocalDate>) andPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isYesterdayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildOrYesterdayPredicate() {
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsYesterday(true);
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsYesterday(true);
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isYesterdayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsYesterday(true);
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsYesterday(true);
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsYesterday(true);
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andYesterdayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> andLowerBound =
                (LiteralExpression<LocalDate>) andYesterdayPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound =
                (LiteralExpression<LocalDate>) andYesterdayPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isYesterdayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isYesterdayPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }
}