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

class BooleanFilterTruePredicateBuilderTest extends BooleanFilterPredicateBuilderTest {

    @Test
    void buildPredicateTrue() {
        BooleanFilter filter = new BooleanFilter();
        filter.setTrue(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicateTrueReverse() {
        BooleanFilter filter = new BooleanFilter();
        filter.setTrue(!VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(!VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildOrTruePredicate() {
        BaseBooleanFilter orFilter = new BaseBooleanFilter();
        orFilter.setTrue(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BooleanAssertionPredicate.class, predicate);

        BooleanAssertionPredicate booleanAssertionPredicate = (BooleanAssertionPredicate) predicate;

        assertEquals(VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildAndTruePredicate() {
        BaseBooleanFilter andFilter = new BaseBooleanFilter();
        andFilter.setTrue(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setTrue(OTHER_VALUE);
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
        assertEquals(!VALUE, andBooleanAssertionPredicate.getAssertedValue());

        BooleanAssertionPredicate normalBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(!OTHER_VALUE, normalBooleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildFullTruePredicate() {
        BaseBooleanFilter andFilter = new BaseBooleanFilter();
        andFilter.setTrue(VALUE);
        BaseBooleanFilter orFilter = new BaseBooleanFilter();
        orFilter.setTrue(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setTrue(OTHER_VALUE);
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
        BooleanAssertionPredicate andtruePredicate = (BooleanAssertionPredicate) andPredicateExpressions.get(0);
        assertEquals(!andFilter.getTrue(), andtruePredicate.getAssertedValue());
        assertInstanceOf(BooleanAssertionPredicate.class, andPredicateExpressions.get(1));
        BooleanAssertionPredicate isTruePredicate = (BooleanAssertionPredicate) andPredicateExpressions.get(1);
        assertEquals(!filter.getTrue(), isTruePredicate.getAssertedValue());
        assertInstanceOf(BooleanAssertionPredicate.class, expressions.get(1));
        BooleanAssertionPredicate orTruePredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(orFilter.getTrue(), orTruePredicate.getAssertedValue());
    }
}