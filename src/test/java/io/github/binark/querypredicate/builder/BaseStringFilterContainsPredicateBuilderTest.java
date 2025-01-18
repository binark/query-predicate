package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator.NOT_EQUAL;
import static org.junit.jupiter.api.Assertions.*;

class BaseStringFilterContainsPredicateBuilderTest extends BaseStringFilterPredicateBuilderTest {

    @Test
    void buildPredicate_Contains() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_Contains_With_Not_Null_Predicate() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setContains(VALUE);

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

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_ContainsIgnoreCase() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setContainsIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_ContainsIgnoreCase_With_Not_Null_Predicate() {
        BaseStringFilter filter = new BaseStringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setContainsIgnoreCase(VALUE);

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

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }
}