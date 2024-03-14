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
class StringFilterStartWithPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildPredicate_StartWith() {
        StringFilter filter = new StringFilter();
        filter.setStartWith(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_StartWith_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setStartWith(VALUE);

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
        assertEquals(VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_Or_StartWith() {
        StringFilter orFilter = new StringFilter();
        orFilter.setStartWith(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_And_With_Or_StartWith() {
        StringFilter orFilter = new StringFilter();
        orFilter.setStartWith(VALUE);
        StringFilter filter = new StringFilter();
        filter.setStartWith(OTHER_VALUE);
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
        assertEquals(OTHER_VALUE + "%", andLiteralExpression.getLiteral());
        assertNull(andLikePredicate.getMatchExpression().toString());

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_StartWithIgnoreCase() {
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_StartWithIgnoreCase_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setStartWithIgnoreCase(VALUE);

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
        assertEquals(VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_Or_StartWithIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setStartWithIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_And_With_Or_StartWithIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setStartWithIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(OTHER_VALUE);
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
        assertEquals(OTHER_VALUE.toUpperCase() + "%", andLiteralExpression.getLiteral());

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(VALUE.toUpperCase() + "%", literalExpression.getLiteral());
    }
}