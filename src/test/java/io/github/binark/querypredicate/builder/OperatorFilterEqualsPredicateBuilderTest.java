package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestObject;
import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
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

class OperatorFilterEqualsPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {

    @Test
    void testBuildSimpleAndEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsEquals(new TestObject());
        filter.setAnd(andFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(ComparisonPredicate.class, predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsEquals(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildIsEqualsPlusAndEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsEquals(new TestObject());
        filter.setAnd(andFilter);
        filter.setIsEquals(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate isEqualsPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, isEqualsPredicate.getComparisonOperator());
        LiteralExpression isEqualsOperand = (LiteralExpression) isEqualsPredicate.getRightHandOperand();
        assertEquals(filter.getIsEquals(), isEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate andEqualsPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, andEqualsPredicate.getComparisonOperator());
        LiteralExpression andEqualsOperand = (LiteralExpression) andEqualsPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsEquals(), andEqualsOperand.getLiteral());
    }

    @Test
    void testBuildSimpleOrEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsEquals(new TestObject());
        filter.setOr(orFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(ComparisonPredicate.class, predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsEquals(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildIsEqualsPlusOrEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsEquals(new TestObject());
        filter.setOr(orFilter);
        filter.setIsEquals(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate isEqualsPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, isEqualsPredicate.getComparisonOperator());
        LiteralExpression isEqualsOperand = (LiteralExpression) isEqualsPredicate.getRightHandOperand();
        assertEquals(filter.getIsEquals(), isEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orEqualsPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, orEqualsPredicate.getComparisonOperator());
        LiteralExpression orEqualsOperand = (LiteralExpression) orEqualsPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsEquals(), orEqualsOperand.getLiteral());
    }

    @Test
    void testBuildAndEqualsPlusOrEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsEquals(new TestObject());
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsEquals(new TestObject());
        filter.setAnd(andFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate andEqualsPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, andEqualsPredicate.getComparisonOperator());
        LiteralExpression andEqualsOperand = (LiteralExpression) andEqualsPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsEquals(), andEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orEqualsPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, orEqualsPredicate.getComparisonOperator());
        LiteralExpression orEqualsOperand = (LiteralExpression) orEqualsPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsEquals(), orEqualsOperand.getLiteral());
    }

    @Test
    void testBuildIsEqualsPlusAndEqualsPlusOrEqualsPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsEquals(new TestObject());
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsEquals(new TestObject());
        filter.setAnd(andFilter);
        filter.setIsEquals(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildEqualsPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andCompoundPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andCompoundPredicate.getOperator());
        List<Expression<Boolean>> andExpressions = andCompoundPredicate.getExpressions();
        assertInstanceOf(ComparisonPredicate.class, andExpressions.get(0));
        ComparisonPredicate isEqualsPredicate = (ComparisonPredicate) andExpressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, isEqualsPredicate.getComparisonOperator());
        LiteralExpression isEqualsOperand = (LiteralExpression) isEqualsPredicate.getRightHandOperand();
        assertEquals(filter.getIsEquals(), isEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, andExpressions.get(1));
        ComparisonPredicate andEqualsPredicate = (ComparisonPredicate) andExpressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, andEqualsPredicate.getComparisonOperator());
        LiteralExpression andEqualsOperand = (LiteralExpression) andEqualsPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsEquals(), andEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orEqualsPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL, orEqualsPredicate.getComparisonOperator());
        LiteralExpression orEqualsOperand = (LiteralExpression) orEqualsPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsEquals(), orEqualsOperand.getLiteral());
    }
}