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

class BaseDateFilterTomorrowPredicateBuilderTest extends BaseDateFilterPredicateBuilderTest {

    protected static final String FIELD_NAME = "tomorrow";

    @Test
    void buildIsTomorrowPredicate() {
        BaseDateFilter dateFilter = new BaseDateFilter();
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
    void buildCombinedIsNotNullAndIsTomorrowPredicates() {
        BaseDateFilter dateFilter = new BaseDateFilter();
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
    void buildIsNotTomorrowPredicate() {
        BaseDateFilter dateFilter = new BaseDateFilter();
        dateFilter.setIsTomorrow(false);
        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

        assertNotNull(negatedPredicateWrapper);
    }
}