package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.DateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateFilterTomorrowPredicateBuilderTest extends DateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "tomorrow";

    @Test
    void buildPredicate_for_tomorrow() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsTomorrow(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_tomorrow_with_not_null_predicate() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setNull(false);
        dateFilter.setIsTomorrow(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
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

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_or_tomorrow() {
        DateFilter orDateFilter = new DateFilter();
        orDateFilter.setIsTomorrow(true);
        DateFilter dateFilter = new DateFilter();
        dateFilter.setOr(orDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_and_with_normal_tomorrow() {
        DateFilter andDateFilter = new DateFilter();
        andDateFilter.setIsTomorrow(true);
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsTomorrow(true);
        dateFilter.setAnd(andDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
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

        LiteralExpression<Date> normalLowerBound = (LiteralExpression<Date>) normalBetweenPredicate.getLowerBound();
        LiteralExpression<Date> normalUpperBound = (LiteralExpression<Date>) normalBetweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), normalLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), normalUpperBound.getLiteral());

        BetweenPredicate andBetweenPredicate = (BetweenPredicate) expressions.get(1);

        assertNotNull(andBetweenPredicate);

        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andBetweenPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andBetweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), andLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))), andUpperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_tomorrow() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsTomorrow(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}