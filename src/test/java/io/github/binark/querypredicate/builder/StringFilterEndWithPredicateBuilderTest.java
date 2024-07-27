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
class StringFilterEndWithPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildPredicate_EndWith() {
        StringFilter filter = new StringFilter();
        filter.setEndWith(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE, literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_EndWith_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setEndWith(VALUE);

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
        assertEquals("%" + VALUE, literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_Or_EndWith() {
        StringFilter orFilter = new StringFilter();
        orFilter.setEndWith(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE, literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_And_With_Or_EndWith() {
        StringFilter orFilter = new StringFilter();
        orFilter.setEndWith(VALUE);
        StringFilter filter = new StringFilter();
        filter.setEndWith(OTHER_VALUE);
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
        assertEquals("%" + OTHER_VALUE, andLiteralExpression.getLiteral());
        assertNull(andLikePredicate.getMatchExpression().toString());

        LikePredicate likePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE, literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildPredicate_EndWithIgnoreCase() {
        StringFilter filter = new StringFilter();
        filter.setEndWithIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase(), literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_EndWithIgnoreCase_With_Not_Null_Predicate() {
        StringFilter filter = new StringFilter();
        filter.setIsDifferent(EQUALS_VALUE);
        filter.setEndWithIgnoreCase(VALUE);

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
        assertEquals("%" + VALUE.toUpperCase(), literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_Or_EndWithIgnoreCase() {
        StringFilter orFilter = new StringFilter();
        orFilter.setEndWithIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase(), literalExpression.getLiteral());
    }

    @Test
    void buildPredicate_And_With_Normal_EndWithIgnoreCase() {
        StringFilter andFilter = new StringFilter();
        andFilter.setEndWithIgnoreCase(VALUE);
        StringFilter filter = new StringFilter();
        filter.setEndWithIgnoreCase(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        LikePredicate normalLikePredicate = (LikePredicate) expressions.get(0);
        LiteralExpression normalLiteralExpression = (LiteralExpression) normalLikePredicate.getPattern();
        assertEquals("%" + OTHER_VALUE.toUpperCase(), normalLiteralExpression.getLiteral());

        LikePredicate andLikePredicate = (LikePredicate) expressions.get(1);
        LiteralExpression andLiteralExpression = (LiteralExpression) andLikePredicate.getPattern();
        assertEquals("%" + VALUE.toUpperCase(), andLiteralExpression.getLiteral());
    }
}