package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator.NOT_EQUAL;
import static org.junit.jupiter.api.Assertions.*;

class BaseStringFilterNotContainsPredicateBuilderTest extends BaseStringFilterPredicateBuilderTest {

    @Test
    void buildNotContainsPredicate() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setNotContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    }

    @Test
    void buildCombinedIsDifferentAndNotContainsPredicates() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setNotContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(0);
        assertNotNull(comparisonPredicate);
        assertEquals(NOT_EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression<String> rightHandOperand = (LiteralExpression<String>) comparisonPredicate.getRightHandOperand();
        assertNotNull(rightHandOperand);
        assertEquals(EQUALS_VALUE, rightHandOperand.getLiteral());

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) expressions.get(1);
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildNotContainsIgnoreCasePredicate() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setNotContainsIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildCombinedIsDifferentAndNotContainsIgnoreCasePredicates() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setNotContainsIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) expressions.get(0);
        assertNotNull(comparisonPredicate);
        assertEquals(NOT_EQUAL, comparisonPredicate.getComparisonOperator());
        LiteralExpression<String> rightHandOperand = (LiteralExpression<String>) comparisonPredicate.getRightHandOperand();
        assertNotNull(rightHandOperand);
        assertEquals(EQUALS_VALUE, rightHandOperand.getLiteral());

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) expressions.get(1);
        assertNotNull(negatedPredicateWrapper);
    }
}