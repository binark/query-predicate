package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.LocalDateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalDateFilterTodayPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "today";

    @Test
    void buildPredicate_for_today() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsToday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
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
    void buildPredicate_for_today_with_not_null_predicate() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setNull(false);
        localDateFilter.setIsToday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_or_today() {
        LocalDateFilter orocalDateFilter = new LocalDateFilter();
        orocalDateFilter.setIsToday(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setOr(orocalDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
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
    void buildPredicate_for_and_with_or_today() {
        LocalDateFilter andLocalDateFilter = new LocalDateFilter();
        andLocalDateFilter.setIsToday(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsToday(true);
        localDateFilter.setAnd(andLocalDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate andBetweenPredicate = (BetweenPredicate) expressions.get(0);

        LiteralExpression<LocalDate> andLowerBound = (LiteralExpression<LocalDate>) andBetweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound = (LiteralExpression<LocalDate>) andBetweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        BetweenPredicate orBetweenPredicate = (BetweenPredicate) expressions.get(1);

        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orBetweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orBetweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_today() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsToday(false);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}