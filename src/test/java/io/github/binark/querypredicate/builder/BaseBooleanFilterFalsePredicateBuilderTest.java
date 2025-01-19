package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseBooleanFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.BooleanAssertionPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseBooleanFilterFalsePredicateBuilderTest extends BaseBooleanFilterPredicateBuilderTest {

    @Test
    void buildIsFalsePredicate() {
        BaseBooleanFilter filter = new BaseBooleanFilter();
        filter.setFalse(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildIsFalseReversePredicate() {
        BaseBooleanFilter filter = new BaseBooleanFilter();
        filter.setFalse(!VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildCombinedIsNotNullAndIsFalsePredicates() {
        BaseBooleanFilter filter = new BaseBooleanFilter();
        filter.setNull(OTHER_VALUE);
        filter.setFalse(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }
}