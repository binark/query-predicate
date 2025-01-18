package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDateFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseDateFilterYesterdayPredicateBuilderTest extends BaseDateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "yesterday";

    @Test
    void buildPredicate_for_yesterday() {
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setIsYesterday(true);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

        assertNotNull(betweenPredicate);

        LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
        LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

        assertEquals(atStartOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24))), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24))), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_for_yesterday_with_not_null_predicate() {
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setNull(false);
        dateFilter.setIsYesterday(true);
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

        assertEquals(atStartOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24))), lowerBound.getLiteral());
        assertEquals(atEndOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24))), upperBound.getLiteral());
    }

    @Test
    void buildPredicate_not_for_yesterday() {
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setIsYesterday(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}