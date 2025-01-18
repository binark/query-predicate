package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringFilterContainsPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildContainsPredicate() {
        StringFilter filter = new StringFilter();
        filter.setContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%" + filter.getContains() + "%", literalExpression.getLiteral());
        assertNull(likePredicate.getMatchExpression().toString());
    }

    @Test
    void buildAndContainsPredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setContains(VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(LikePredicate.class, expressions.get(0));
        LikePredicate isPredicate = (LikePredicate) expressions.get(0);
        LiteralExpression isLiteralExpression = (LiteralExpression) isPredicate.getPattern();
        assertEquals("%" + filter.getContains() + "%", isLiteralExpression.getLiteral());
        assertNull(isPredicate.getMatchExpression().toString());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate andPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression andLiteralExpression = (LiteralExpression) andPredicate.getPattern();
        assertEquals("%" + andFilter.getContains() + "%", andLiteralExpression.getLiteral());
        assertNull(andPredicate.getMatchExpression().toString());
    }

    @Test
    void buildOrContainsPredicate() {
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setContains(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(LikePredicate.class, expressions.get(0));
        LikePredicate isPredicate = (LikePredicate) expressions.get(0);
        LiteralExpression isLiteralExpression = (LiteralExpression) isPredicate.getPattern();
        assertEquals("%" + filter.getContains() + "%", isLiteralExpression.getLiteral());
        assertNull(isPredicate.getMatchExpression().toString());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate orPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression orLiteralExpression = (LiteralExpression) orPredicate.getPattern();
        assertEquals("%" + orFilter.getContains() + "%", orLiteralExpression.getLiteral());
        assertNull(orPredicate.getMatchExpression().toString());
    }

    @Test
    void buildFullContainsPredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setContains("foo");
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setContains(VALUE);
        filter.setAnd(andFilter);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andCompoundPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(AND, andCompoundPredicate.getOperator().name());
        List<Expression<Boolean>> andExpressions = andCompoundPredicate.getExpressions();

        assertInstanceOf(LikePredicate.class, andExpressions.get(0));
        LikePredicate isPredicate = (LikePredicate) andExpressions.get(0);
        LiteralExpression isLiteralExpression = (LiteralExpression) isPredicate.getPattern();
        assertEquals("%" + filter.getContains() + "%", isLiteralExpression.getLiteral());
        assertNull(isPredicate.getMatchExpression().toString());

        assertInstanceOf(LikePredicate.class, andExpressions.get(1));
        LikePredicate andPredicate = (LikePredicate) andExpressions.get(1);
        LiteralExpression andLiteralExpression = (LiteralExpression) andPredicate.getPattern();
        assertEquals("%" + andFilter.getContains() + "%", andLiteralExpression.getLiteral());
        assertNull(andPredicate.getMatchExpression().toString());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate orPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression orLiteralExpression = (LiteralExpression) orPredicate.getPattern();
        assertEquals("%" + orFilter.getContains() + "%", orLiteralExpression.getLiteral());
        assertNull(orPredicate.getMatchExpression().toString());
    }
}