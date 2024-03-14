package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator.NOT_EQUAL;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StringFilterNotContainsPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildPredicate_Not_Contains() {
        StringFilter filter = new StringFilter();
        filter.setNotContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildPredicate_Not_Contains_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
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
    void buildPredicate_Or_Not_Contains() {
        StringFilter orFilter = new StringFilter();
        orFilter.setNotContains(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildPredicate_And_With_Or_Not_Contains() {
        StringFilter orFilter = new StringFilter();
        orFilter.setNotContains(VALUE);
        StringFilter filter = new StringFilter();
        filter.setNotContains(OTHER_VALUE);
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
        assertTrue(expressions.stream().allMatch(expression -> expression instanceof NegatedPredicateWrapper));
    }

    @Test
    void buildPredicate_Not_ContainsIgnoreCase() {
        StringFilter filter = new StringFilter();
        filter.setNotContainsIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildPredicate_Not_ContainsIgnoreCase_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
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

    @Test
    void buildPredicate_Or_Not_ContainsIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setNotContainsIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
        assertNotNull(negatedPredicateWrapper);
    }

    @Test
    void buildPredicate_And_With_Or_Not_ContainsIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setNotContainsIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setNotContainsIgnoreCase(OTHER_VALUE);
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
        assertTrue(expressions.stream().allMatch(expression -> expression instanceof NegatedPredicateWrapper));
    }
}