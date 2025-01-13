package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class OperatorFilterIsNotNullPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {

    @Test
    void testBuildSimpleAndNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(false);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    }

    @Test
    void testBuildIsNotNullPlusAndNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(false);
        filter.setAnd(andFilter);
        filter.setNull(false);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }

    @Test
    void testBuildSimpleOrNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(false);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    }

    @Test
    void testBuildIsNotNullPlusOrNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(false);
        filter.setOr(orFilter);
        filter.setNull(false);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }

    @Test
    void testBuildAndNotNullPlusOrNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(false);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(false);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }

    @Test
    void testBuildIsNotNullPlusAndNotNullPlusOrNotNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(false);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(false);
        filter.setAnd(andFilter);
        filter.setNull(false);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andCompoundPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andCompoundPredicate.getOperator());
        List<Expression<Boolean>> andExpressions = andCompoundPredicate.getExpressions();
        assertInstanceOf(NegatedPredicateWrapper.class, andExpressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, andExpressions.get(1));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }
}