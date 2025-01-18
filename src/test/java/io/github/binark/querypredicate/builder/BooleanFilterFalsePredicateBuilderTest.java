package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseBooleanFilter;
import io.github.binark.querypredicate.filter.BooleanFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.BooleanAssertionPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void buildOrFalsePredicate() {
        BaseBooleanFilter orFilter = new BaseBooleanFilter();
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
    void buildAndFalsePredicate() {
        BaseBooleanFilter andFilter = new BaseBooleanFilter();
        andFilter.setFalse(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setFalse(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BooleanAssertionPredicate andBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(0);
        assertEquals(VALUE, andBooleanAssertionPredicate.getAssertedValue());

        BooleanAssertionPredicate normalBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(OTHER_VALUE, normalBooleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildFullFalsePredicate() {
        BaseBooleanFilter andFilter = new BaseBooleanFilter();
        andFilter.setFalse(VALUE);
        BaseBooleanFilter orFilter = new BaseBooleanFilter();
        orFilter.setFalse(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setFalse(OTHER_VALUE);
        filter.setAnd(andFilter);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andPredicate.getOperator());
        List<Expression<Boolean>> andPredicateExpressions = andPredicate.getExpressions();
        assertInstanceOf(BooleanAssertionPredicate.class, andPredicateExpressions.get(0));
        BooleanAssertionPredicate andFalsePredicate = (BooleanAssertionPredicate) andPredicateExpressions.get(0);
        assertEquals(andFilter.getFalse(), andFalsePredicate.getAssertedValue());
        assertInstanceOf(BooleanAssertionPredicate.class, andPredicateExpressions.get(1));
        BooleanAssertionPredicate isFalsePredicate = (BooleanAssertionPredicate) andPredicateExpressions.get(1);
        assertEquals(filter.getFalse(), isFalsePredicate.getAssertedValue());
        assertInstanceOf(BooleanAssertionPredicate.class, expressions.get(1));
        BooleanAssertionPredicate orFalsePredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(!orFilter.getFalse(), orFalsePredicate.getAssertedValue());
    }
}