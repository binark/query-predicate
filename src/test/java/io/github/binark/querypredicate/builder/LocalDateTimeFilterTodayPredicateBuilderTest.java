package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseLocalDateTimeFilter;
import io.github.binark.querypredicate.filter.LocalDateTimeFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeFilterTodayPredicateBuilderTest extends LocalDateTimeFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "today";

    @Test
    void buildTodayPredicate() {
        LocalDateTimeFilter filter = new LocalDateTimeFilter();
        filter.setIsToday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        LiteralExpression<LocalDateTime> lowerBound =
                (LiteralExpression<LocalDateTime>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> upperBound =
                (LiteralExpression<LocalDateTime>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), upperBound.getLiteral());
    }

    @Test
    void buildAndTodayPredicate() {
        BaseLocalDateTimeFilter andFilter = new BaseLocalDateTimeFilter();
        andFilter.setIsToday(true);
        LocalDateTimeFilter filter = new LocalDateTimeFilter();
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
        LiteralExpression<LocalDateTime> andLowerBound =
                (LiteralExpression<LocalDateTime>) andPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> andUpperBound =
                (LiteralExpression<LocalDateTime>) andPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDateTime> isLowerBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> isUpperBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), isUpperBound.getLiteral());
    }

    @Test
    void buildOrTodayPredicate() {
        BaseLocalDateTimeFilter orFilter = new BaseLocalDateTimeFilter();
        orFilter.setIsToday(true);
        LocalDateTimeFilter filter = new LocalDateTimeFilter();
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
        LiteralExpression<LocalDateTime> orLowerBound = (LiteralExpression<LocalDateTime>) orPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> orUpperBound = (LiteralExpression<LocalDateTime>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), orUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, expressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) expressions.get(1);
        LiteralExpression<LocalDateTime> isLowerBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> isUpperBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), isUpperBound.getLiteral());
    }

    @Test
    void buildFullTodayPredicate() {
        BaseLocalDateTimeFilter andFilter = new BaseLocalDateTimeFilter();
        andFilter.setIsToday(true);
        BaseLocalDateTimeFilter orFilter = new BaseLocalDateTimeFilter();
        orFilter.setIsToday(true);
        LocalDateTimeFilter filter = new LocalDateTimeFilter();
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
        LiteralExpression<LocalDateTime> orLowerBound = (LiteralExpression<LocalDateTime>) orPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> orUpperBound = (LiteralExpression<LocalDateTime>) orPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), orUpperBound.getLiteral());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);

        assertEquals(AND, andPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andPredicate.getExpressions();

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate andTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDateTime> andLowerBound =
                (LiteralExpression<LocalDateTime>) andTodayPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> andUpperBound =
                (LiteralExpression<LocalDateTime>) andTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), andUpperBound.getLiteral());

        assertInstanceOf(BetweenPredicate.class, andExpressions.get(1));
        BetweenPredicate isTodayPredicate = (BetweenPredicate) andExpressions.get(1);
        LiteralExpression<LocalDateTime> isLowerBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getLowerBound();
        LiteralExpression<LocalDateTime> isUpperBound =
                (LiteralExpression<LocalDateTime>) isTodayPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay(), isLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX), isUpperBound.getLiteral());
    }
}