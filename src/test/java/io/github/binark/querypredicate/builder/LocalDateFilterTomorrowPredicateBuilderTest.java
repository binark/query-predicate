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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateFilterTomorrowPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "tomorrow";

    @Test
    void buildTomorrowPredicate() {
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsTomorrow(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildAndTomorrowPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsTomorrow(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTomorrowPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTomorrowPredicate() {
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsTomorrow(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTomorrowPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsTomorrow(true);
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsTomorrow(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andTomorrowPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> andLowerBound =
                (LiteralExpression<LocalDate>) andTomorrowPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound =
                (LiteralExpression<LocalDate>) andTomorrowPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTomorrowPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTomorrowPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }
}