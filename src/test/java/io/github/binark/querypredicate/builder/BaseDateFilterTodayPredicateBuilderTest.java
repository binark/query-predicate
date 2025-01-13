package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDateFilter;
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
class BaseDateFilterTodayPredicateBuilderTest extends BaseDateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "today";

    @Test
    void buildPredicate_for_today() {
        BaseDateFilter dateFilter = new BaseDateFilter();
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
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setIsToday(true);
        dateFilter.setNull(false);
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

        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
    }

//    @Test
//    void buildPredicate_for_or_today() {
//        BaseDateFilter orDateFilter = new BaseDateFilter();
//        orDateFilter.setIsToday(true);
//        BaseDateFilter dateFilter = new BaseDateFilter();
//        dateFilter.setOr(orDateFilter);
//
//        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
//                FIELD_NAME);
//
//        assertNotNull(predicate);
//        assertInstanceOf(BetweenPredicate.class, predicate);
//
//        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;
//
//        assertNotNull(betweenPredicate);
//
//        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
//        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();
//
//        assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
//        assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
//    }

//    @Test
//    void buildPredicate_for_and_with_normal_today() {
//        BaseDateFilter andDateFilter = new BaseDateFilter();
//        andDateFilter.setIsToday(true);
//        BaseDateFilter dateFilter = new BaseDateFilter();
//        dateFilter.setIsToday(true);
//        dateFilter.setAnd(andDateFilter);
//
//        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
//                FIELD_NAME);
//
//        assertNotNull(predicate);
//        assertInstanceOf(CompoundPredicate.class, predicate);
//
//        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
//        assertEquals(AND, compoundPredicate.getOperator().name());
//
//        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
//        assertNotNull(expressions);
//        assertEquals(2, expressions.size());
//
//        BetweenPredicate normalBetweenPredicate = (BetweenPredicate) expressions.get(0);
//
//        assertNotNull(normalBetweenPredicate);
//
//        LiteralExpression<Date> normalLowerBound = (LiteralExpression<Date>) normalBetweenPredicate.getLowerBound();
//        LiteralExpression<Date> normalUpperBound = (LiteralExpression<Date>) normalBetweenPredicate.getUpperBound();
//
//        assertEquals(atStartOfDay(new Date()), normalLowerBound.getLiteral());
//        assertEquals(atEndOfDay(new Date()), normalUpperBound.getLiteral());
//
//        BetweenPredicate andBetweenPredicate = (BetweenPredicate) expressions.get(1);
//
//        assertNotNull(andBetweenPredicate);
//
//        LiteralExpression<Date> andLowerBound = (LiteralExpression<Date>) andBetweenPredicate.getLowerBound();
//        LiteralExpression<Date> andUpperBound = (LiteralExpression<Date>) andBetweenPredicate.getUpperBound();
//
//        assertEquals(atStartOfDay(new Date()), andLowerBound.getLiteral());
//        assertEquals(atEndOfDay(new Date()), andUpperBound.getLiteral());
//    }

    @Test
    void buildPredicate_not_for_today() {
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setIsToday(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}