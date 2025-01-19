package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseComparableFilter;
import io.github.binark.querypredicate.testdouble.TestComparableFilter;
import io.github.binark.querypredicate.testdouble.TestComparableObject;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

public class ComparableFilterLessThanPredicateBuilderTest extends ComparableFilterPredicateBuilderTestInit {

    @Test
    void testBuildIsLessThanPredicate() {
        TestComparableFilter filter = new TestComparableFilter();
        filter.setIsLessThan(new TestComparableObject());
        when(path.getJavaType()).thenReturn(TestComparableObject.class);

        Predicate predicate = predicateBuilder.addLessThanPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                    FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(filter.getIsLessThan(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildAndLessThanPredicate() {
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter andFilter = new TestBaseComparableFilter();
        andFilter.setIsLessThan(new TestComparableObject());
        filter.setAnd(andFilter);
        when(path.getJavaType()).thenReturn(TestComparableObject.class);

        Predicate predicate = predicateBuilder.addLessThanPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                    FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsLessThan(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildOrLessThanPredicate() {
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter orFilter = new TestBaseComparableFilter();
        orFilter.setIsLessThan(new TestComparableObject());
        filter.setOr(orFilter);
        when(path.getJavaType()).thenReturn(TestComparableObject.class);

        Predicate predicate = predicateBuilder.addLessThanPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                    FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(basePredicate, expressions.get(0));
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsLessThan(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildFullLessThanPredicate() {
        TestComparableFilter filter = new TestComparableFilter();
        TestBaseComparableFilter orFilter = new TestBaseComparableFilter();
        orFilter.setIsLessThan(new TestComparableObject());
        filter.setOr(orFilter);
        TestBaseComparableFilter andFilter = new TestBaseComparableFilter();
        andFilter.setIsLessThan(new TestComparableObject());
        filter.setAnd(andFilter);
        filter.setIsLessThan(new TestComparableObject());
        when(path.getJavaType()).thenReturn(TestComparableObject.class);

        Predicate predicate = predicateBuilder.addLessThanPredicate(basePredicate, filter, criteriaBuilder, path,
                                                                    FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andPredicate.getOperator());
        List<Expression<Boolean>> andPredicateExpressions = andPredicate.getExpressions();
        assertInstanceOf(CompoundPredicate.class, andPredicateExpressions.get(0));
        CompoundPredicate isPredicate = (CompoundPredicate) andPredicateExpressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, isPredicate.getOperator());
        List<Expression<Boolean>> isPredicateExpressions = isPredicate.getExpressions();
        assertEquals(basePredicate, isPredicateExpressions.get(0));
        assertInstanceOf(ComparisonPredicate.class, isPredicateExpressions.get(1));
        ComparisonPredicate isGreaterThanPredicate = (ComparisonPredicate) isPredicateExpressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, isGreaterThanPredicate.getComparisonOperator());
        LiteralExpression isGreaterThanRightOperand = (LiteralExpression) isGreaterThanPredicate.getRightHandOperand();
        assertEquals(filter.getIsLessThan(), isGreaterThanRightOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, andPredicateExpressions.get(1));
        ComparisonPredicate andGreaterThanPredicate = (ComparisonPredicate) andPredicateExpressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, andGreaterThanPredicate.getComparisonOperator());
        LiteralExpression andGreaterThanPredicateRightHandOperand =
                (LiteralExpression) andGreaterThanPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsLessThan(), andGreaterThanPredicateRightHandOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN, orPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) orPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsLessThan(), rightHandOperand.getLiteral());
    }
}
