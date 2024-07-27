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
class LocalDateFilterYesterdayPredicateBuilderTest extends LocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "yesterday";

    @Test
    void buildPredicate_for_yesterday() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsYesterday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_yesterday_with_not_null_predicate() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setNull(false);
        localDateFilter.setIsYesterday(true);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);

        assertNotNull(betweenPredicate);

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_or_yesterday() {
        LocalDateFilter orLocalDateFilter = new LocalDateFilter();
        orLocalDateFilter.setIsYesterday(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setOr(orLocalDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_and_with_normal_yesterday() {
        LocalDateFilter andLocalDateFilter = new LocalDateFilter();
        andLocalDateFilter.setIsYesterday(true);
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsYesterday(true);
        localDateFilter.setAnd(andLocalDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate normalBetweenPredicate = (BetweenPredicate) expressions.get(0);

        assertNotNull(normalBetweenPredicate);

        LiteralExpression<LocalDate> normalLowerBound = (LiteralExpression<LocalDate>) normalBetweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> normalUpperBound = (LiteralExpression<LocalDate>) normalBetweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), normalLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), normalUpperBound.getLiteral());

        BetweenPredicate andBetweenPredicate = (BetweenPredicate) expressions.get(1);

        assertNotNull(andBetweenPredicate);

        LiteralExpression<LocalDate> andLowerBound = (LiteralExpression<LocalDate>) andBetweenPredicate.getLowerBound();
        LiteralExpression<LocalDate> andUpperBound = (LiteralExpression<LocalDate>) andBetweenPredicate.getUpperBound();

        assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), andLowerBound.getLiteral());
        assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), andUpperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_yesterday() {
        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setIsYesterday(false);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}