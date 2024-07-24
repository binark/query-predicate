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
class DateFilterTodayPredicateBuilderTest extends DateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "today";

    @Test
    void buildPredicate_for_today() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsToday(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_today_With_Not_Null_Predicate() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsToday(true);
        dateFilter.setNull(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate betweenPredicate = (BetweenPredicate) expressions.get(1);

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_or_today() {
        DateFilter orDateFilter = new DateFilter();
        orDateFilter.setIsToday(true);
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

        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_and_with_or_today() {
        DateFilter andDateFilter = new DateFilter();
        andDateFilter.setIsToday(true);
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsToday(true);
        dateFilter.setAnd(andDateFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate andBetweenPredicate = (BetweenPredicate) expressions.get(0);

        assertNotNull(andBetweenPredicate);

        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andBetweenPredicate.getLowerBound();
        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andBetweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), andLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), andUpperBound.getLiteral());

        BetweenPredicate orBetweenPredicate = (BetweenPredicate) expressions.get(1);

        assertNotNull(orBetweenPredicate);

        LiteralExpression<Date> orLowerBound = (LiteralExpression<Date>) orBetweenPredicate.getLowerBound();
        LiteralExpression<Date> orUpperBound = (LiteralExpression<Date>) orBetweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date()), orLowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), orUpperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_today() {
        DateFilter dateFilter = new DateFilter();
        dateFilter.setIsToday(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}