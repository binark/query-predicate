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
    void buildPredicate_False() {
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
    void buildPredicate_False_Reverse() {
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
    void buildPredicate_False_With_Not_Null_Predicate() {
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

//    @Test
//    void buildPredicate_Or_False() {
//        BaseBooleanFilter orFilter = new BaseBooleanFilter();
//        orFilter.setFalse(VALUE);
//        BaseBooleanFilter filter = new BaseBooleanFilter();
//        filter.setOr(orFilter);
//
//        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//                FIELD_NAME);
//
//        assertNotNull(predicate);
//        assertInstanceOf(BooleanAssertionPredicate.class, predicate);
//
//        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;
//
//        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
//    }

//    @Test
//    void buildPredicate_And_With_Normal_False() {
//        BaseBooleanFilter andFilter = new BaseBooleanFilter();
//        andFilter.setFalse(VALUE);
//        BaseBooleanFilter filter = new BaseBooleanFilter();
//        filter.setFalse(OTHER_VALUE);
//        filter.setAnd(andFilter);
//
//        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
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
//        BooleanAssertionPredicate andBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(0);
//        assertEquals(VALUE, andBooleanAssertionPredicate.getAssertedValue());
//
//        BooleanAssertionPredicate normalBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
//        assertEquals(OTHER_VALUE, normalBooleanAssertionPredicate.getAssertedValue());
//    }
}