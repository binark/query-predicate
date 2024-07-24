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
class LocalDateFilterTomorrowPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "tomorrow";

    @Test
    void buildPredicate_for_tomorrow() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsTomorrow(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
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
    void buildPredicate_for_tomorrow_with_not_null_predicate() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setNull(false);
        localDateFilter.setIsTomorrow(true);

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

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_or_tomorrow() {
        LocalDateFilter orLocalDateFilter = new LocalDateFilter();
        orLocalDateFilter.setIsTomorrow(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setOr(orLocalDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
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
    void buildPredicate_for_and_with_or_tomorrow() {
        LocalDateFilter andLocalDateFilter = new LocalDateFilter();
        andLocalDateFilter.setIsTomorrow(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsTomorrow(true);
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

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());

        BetweenPredicate orBetweenPredicate = (BetweenPredicate) expressions.get(1);

        LiteralExpression<LocalDate> orLowerBound = (LiteralExpression<LocalDate>) orBetweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> orUpperBound = (LiteralExpression<LocalDate>) orBetweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), orLowerBound.getLiteral());
        assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), orUpperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_tomorrow() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsTomorrow(false);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}