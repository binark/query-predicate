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

class LocalDateFilterTodayPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "today";

    @Test
    void buildTodayPredicate() {
        LocalDateFilter filter = new LocalDateFilter();
        filter.setIsToday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildAndTodayPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsToday(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<LocalDate> andLowerBound = (LiteralExpression<LocalDate>) andPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound = (LiteralExpression<LocalDate>) andPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTodayPredicate() {
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsToday(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseLocalDateFilter andFilter = new BaseLocalDateFilter();
        andFilter.setIsToday(true);
        BaseLocalDateFilter orFilter = new BaseLocalDateFilter();
        orFilter.setIsToday(true);
        LocalDateFilter filter = new LocalDateFilter();
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
        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> andLowerBound = (LiteralExpression<LocalDate>) andTodayPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound = (LiteralExpression<LocalDate>) andTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDate> isLowerBound = (LiteralExpression<LocalDate>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDate> isUpperBound = (LiteralExpression<LocalDate>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), isUpperBound.getLiteral());
    }
}