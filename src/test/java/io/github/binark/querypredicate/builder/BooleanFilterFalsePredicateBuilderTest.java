package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.BooleanAssertionPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BooleanFilterFalsePredicateBuilderTest extends BooleanFilterPredicateBuilderTest {

    @Test
    void buildPredicate_False() {
        BooleanFilter filter = new BooleanFilter();
        filter.setFalse(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicate_False_Reverse() {
        BooleanFilter filter = new BooleanFilter();
        filter.setFalse(!VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicate_False_With_Not_Null_Predicate() {
        BooleanFilter filter = new BooleanFilter();
        filter.setNull(OTHER_VALUE);
        filter.setFalse(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicate_Or_False() {
        BooleanFilter orFilter = new BooleanFilter();
        orFilter.setFalse(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicate_And_With_Or_False() {
        BooleanFilter andFilter = new BooleanFilter();
        andFilter.setFalse(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setFalse(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BooleanAssertionPredicate andBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(0);
        assertEquals(!VALUE, andBooleanAssertionPredicate.getAssertedValue());

        BooleanAssertionPredicate orBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(!OTHER_VALUE, orBooleanAssertionPredicate.getAssertedValue());
    }
}