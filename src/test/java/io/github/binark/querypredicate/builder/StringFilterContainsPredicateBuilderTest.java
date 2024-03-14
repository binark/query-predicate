package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator.NOT_EQUAL;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StringFilterContainsPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildPredicate_Contains() {
        StringFilter filter = new StringFilter();
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
        StringFilter filter = new StringFilter();
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
    void buildPredicate_Or_Contains() {
        StringFilter orFilter = new StringFilter();
        orFilter.setContains(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

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
    void buildPredicate_And_With_Or_Contains() {
        StringFilter orFilter = new StringFilter();
        orFilter.setContains(VALUE);
        StringFilter filter = new StringFilter();
        filter.setContains(OTHER_VALUE);
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

        LikePredicate andLikePredicate = (LikePredicate) expressions.get(0);
        LiteralExpression andLiteralExpression = (LiteralExpression) andLikePredicate.getPattern();
        assertEquals("%" + OTHER_VALUE + "%", andLiteralExpression.getLiteral());
        assertNull(andLikePredicate.getMatchExpression().toString());

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_ContainsIgnoreCase() {
        StringFilter filter = new StringFilter();
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
        StringFilter filter = new StringFilter();
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

    @Test
    void buildPredicate_Or_ContainsIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setContainsIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_And_With_Or_ContainsIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setContainsIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setContainsIgnoreCase(OTHER_VALUE);
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

        LikePredicate andLikePredicate = (LikePredicate) expressions.get(0);
        LiteralExpression andLiteralExpression = (LiteralExpression) andLikePredicate.getPattern();
        assertEquals("%" + OTHER_VALUE.toUpperCase() + "%", andLiteralExpression.getLiteral());

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }
}