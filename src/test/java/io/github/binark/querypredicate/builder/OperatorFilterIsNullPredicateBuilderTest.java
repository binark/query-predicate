package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NullnessPredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class OperatorFilterIsNullPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {

    @Test
    void testBuildSimpleAndNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(true);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(NullnessPredicate.class, predicate);
        NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
        assertEquals(path, nullnessPredicate.getOperand());
    }

    @Test
    void testBuildIsNullPlusAndNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(true);
        filter.setAnd(andFilter);
        filter.setNull(true);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NullnessPredicate.class, expressions.get(0));
        NullnessPredicate isNullPredicate = (NullnessPredicate) expressions.get(0);
        assertEquals(path, isNullPredicate.getOperand());
        assertInstanceOf(NullnessPredicate.class, expressions.get(1));
        NullnessPredicate andNullPredicate = (NullnessPredicate) expressions.get(1);
        assertEquals(path, andNullPredicate.getOperand());
    }

    @Test
    void testBuildSimpleOrNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(true);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(NullnessPredicate.class, predicate);
        NullnessPredicate nullnessPredicate = (NullnessPredicate) predicate;
        assertEquals(path, nullnessPredicate.getOperand());
    }

    @Test
    void testBuildIsNullPlusOrNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(true);
        filter.setOr(orFilter);
        filter.setNull(true);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NullnessPredicate.class, expressions.get(0));
        NullnessPredicate isNullPredicate = (NullnessPredicate) expressions.get(0);
        assertEquals(path, isNullPredicate.getOperand());
        assertInstanceOf(NullnessPredicate.class, expressions.get(1));
        NullnessPredicate orNullPredicate = (NullnessPredicate) expressions.get(1);
        assertEquals(path, orNullPredicate.getOperand());
    }

    @Test
    void testBuildAndNullPlusOrNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(true);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(true);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildIsNullPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NullnessPredicate.class, expressions.get(0));
        NullnessPredicate andNullPredicate = (NullnessPredicate) expressions.get(0);
        assertEquals(path, andNullPredicate.getOperand());
        assertInstanceOf(NullnessPredicate.class, expressions.get(1));
        NullnessPredicate orNullPredicate = (NullnessPredicate) expressions.get(1);
        assertEquals(path, orNullPredicate.getOperand());
    }

    @Test
    void testBuildIsNullPlusAndNullPlusOrNullPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setNull(true);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setNull(true);
        filter.setAnd(andFilter);
        filter.setNull(true);

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
        assertInstanceOf(NullnessPredicate.class, andExpressions.get(0));
        NullnessPredicate isNullPredicate = (NullnessPredicate) andExpressions.get(0);
        assertEquals(path, isNullPredicate.getOperand());
        assertInstanceOf(NullnessPredicate.class, andExpressions.get(1));
        NullnessPredicate andNullPredicate = (NullnessPredicate) andExpressions.get(1);
        assertEquals(path, andNullPredicate.getOperand());
        assertInstanceOf(NullnessPredicate.class, expressions.get(1));
        NullnessPredicate orNullPredicate = (NullnessPredicate) expressions.get(1);
        assertEquals(path, orNullPredicate.getOperand());
    }
}