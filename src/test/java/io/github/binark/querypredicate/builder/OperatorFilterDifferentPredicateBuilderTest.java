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

class OperatorFilterDifferentPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {

    @Test
    void testBuildSimpleAndDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsDifferent(new TestObject());
        filter.setAnd(andFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(ComparisonPredicate.class, predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsDifferent(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildIsDifferentPlusAndDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsDifferent(new TestObject());
        filter.setAnd(andFilter);
        filter.setIsDifferent(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate isDifferentPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, isDifferentPredicate.getComparisonOperator());
        LiteralExpression isEqualsOperand = (LiteralExpression) isDifferentPredicate.getRightHandOperand();
        assertEquals(filter.getIsDifferent(), isEqualsOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate andDifferentPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, andDifferentPredicate.getComparisonOperator());
        LiteralExpression andEqualsOperand = (LiteralExpression) andDifferentPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsDifferent(), andEqualsOperand.getLiteral());
    }

    @Test
    void testBuildSimpleOrDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsDifferent(new TestObject());
        filter.setOr(orFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(ComparisonPredicate.class, predicate);
        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression rightHandOperand = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsDifferent(), rightHandOperand.getLiteral());
    }

    @Test
    void testBuildIsDifferentPlusOrDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsDifferent(new TestObject());
        filter.setOr(orFilter);
        filter.setIsDifferent(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate isDifferentPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, isDifferentPredicate.getComparisonOperator());
        LiteralExpression isDifferentOperand = (LiteralExpression) isDifferentPredicate.getRightHandOperand();
        assertEquals(filter.getIsDifferent(), isDifferentOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orDifferentPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, orDifferentPredicate.getComparisonOperator());
        LiteralExpression orDifferentOperand = (LiteralExpression) orDifferentPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsDifferent(), orDifferentOperand.getLiteral());
    }

    @Test
    void testBuildAndDifferentPlusOrDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsDifferent(new TestObject());
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsDifferent(new TestObject());
        filter.setAnd(andFilter);
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(0));
        ComparisonPredicate andDifferentPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, andDifferentPredicate.getComparisonOperator());
        LiteralExpression andDifferentOperand = (LiteralExpression) andDifferentPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsDifferent(), andDifferentOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orDifferentPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, orDifferentPredicate.getComparisonOperator());
        LiteralExpression orDifferentOperand = (LiteralExpression) orDifferentPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsDifferent(), orDifferentOperand.getLiteral());
    }

    @Test
    void testBuildIsDifferentPlusAndDifferentPlusOrDifferentPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsDifferent(new TestObject());
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsDifferent(new TestObject());
        filter.setAnd(andFilter);
        filter.setIsDifferent(new TestObject());
        when(path.getJavaType()).thenReturn(TestObject.class);

        Predicate predicate = predicateBuilder.buildDifferentPredicate(filter, criteriaBuilder, path, FIELD_NAME);

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
        ComparisonPredicate isDifferentPredicate = (ComparisonPredicate) andExpressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, isDifferentPredicate.getComparisonOperator());
        LiteralExpression isDifferentOperand = (LiteralExpression) isDifferentPredicate.getRightHandOperand();
        assertEquals(filter.getIsDifferent(), isDifferentOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, andExpressions.get(1));
        ComparisonPredicate andDifferentPredicate = (ComparisonPredicate) andExpressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, andDifferentPredicate.getComparisonOperator());
        LiteralExpression andDifferentOperand = (LiteralExpression) andDifferentPredicate.getRightHandOperand();
        assertEquals(andFilter.getIsDifferent(), andDifferentOperand.getLiteral());
        assertInstanceOf(ComparisonPredicate.class, expressions.get(1));
        ComparisonPredicate orDifferentPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonPredicate.ComparisonOperator.NOT_EQUAL, orDifferentPredicate.getComparisonOperator());
        LiteralExpression orDifferentOperand = (LiteralExpression) orDifferentPredicate.getRightHandOperand();
        assertEquals(orFilter.getIsDifferent(), orDifferentOperand.getLiteral());
    }
}