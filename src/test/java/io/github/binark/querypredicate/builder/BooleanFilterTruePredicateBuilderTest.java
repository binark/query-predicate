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
class BooleanFilterTruePredicateBuilderTest extends BooleanFilterPredicateBuilderTest {

    @Test
    void buildPredicate_True() {
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
    void buildPredicate_True_Reverse() {
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
    void buildPredicate_True_With_Not_Null_Predicate() {
        BooleanFilter filter = new BooleanFilter();
        filter.setNull(OTHER_VALUE);
        filter.setTrue(VALUE);

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
        assertEquals(VALUE, booleanAssertionPredicate.getAssertedValue());
    }

    @Test
    void buildPredicate_Or_True() {
        BooleanFilter orFilter = new BooleanFilter();
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
    void buildPredicate_And_With_Or_True() {
        BooleanFilter orFilter = new BooleanFilter();
        orFilter.setTrue(VALUE);
        BooleanFilter filter = new BooleanFilter();
        filter.setTrue(OTHER_VALUE);
        filter.setOr(orFilter);

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
        assertEquals(OTHER_VALUE, andBooleanAssertionPredicate.getAssertedValue());

        BooleanAssertionPredicate orBooleanAssertionPredicate = (BooleanAssertionPredicate) expressions.get(1);
        assertEquals(VALUE, orBooleanAssertionPredicate.getAssertedValue());
    }
}