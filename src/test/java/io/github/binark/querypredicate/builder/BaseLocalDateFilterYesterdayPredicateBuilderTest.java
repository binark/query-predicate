package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseLocalDateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseLocalDateFilterYesterdayPredicateBuilderTest extends BaseLocalDateFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "yesterday";

    @Test
    void buildIsYesterdayPredicate() {
        BaseLocalDateFilter localDateFilter = new BaseLocalDateFilter();
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
    void buildCombinedIsNotNullAndIsYesterdayPredicates() {
        BaseLocalDateFilter localDateFilter = new BaseLocalDateFilter();
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
    void buildIsNotYesterdayPredicate() {
        BaseLocalDateFilter localDateFilter = new BaseLocalDateFilter();
        localDateFilter.setIsYesterday(false);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}