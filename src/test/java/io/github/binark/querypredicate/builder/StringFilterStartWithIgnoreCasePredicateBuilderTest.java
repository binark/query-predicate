package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.expression.function.UpperFunction;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringFilterStartWithIgnoreCasePredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildStartWithIgnoreCasePredicate() {
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(LikePredicate.class, predicate);
        LikePredicate likePredicate = (LikePredicate) predicate;
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals(toUpperCase(filter.getStartWithIgnoreCase() + "%"), literalExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());
    }

    @Test
    void buildAndStartWithIgnoreCasePredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setStartWithIgnoreCase(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(VALUE);
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
        assertEquals(toUpperCase(filter.getStartWithIgnoreCase() + "%"), isLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, isPredicate.getMatchExpression());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate andPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression andLiteralExpression = (LiteralExpression) andPredicate.getPattern();
        assertEquals(toUpperCase(andFilter.getStartWithIgnoreCase() + "%"), andLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, andPredicate.getMatchExpression());
    }

    @Test
    void buildOrStartWithIgnoreCasePredicate() {
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setStartWithIgnoreCase(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(VALUE);
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
        assertEquals(toUpperCase(filter.getStartWithIgnoreCase() + "%"), isLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, isPredicate.getMatchExpression());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate orPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression orLiteralExpression = (LiteralExpression) orPredicate.getPattern();
        assertEquals(toUpperCase(orFilter.getStartWithIgnoreCase() + "%"), orLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, orPredicate.getMatchExpression());
    }

    @Test
    void buildFullStartWithIgnoreCasePredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setStartWithIgnoreCase("foo");
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setStartWithIgnoreCase(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setStartWithIgnoreCase(VALUE);
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
        assertEquals(toUpperCase(filter.getStartWithIgnoreCase() + "%"), isLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, isPredicate.getMatchExpression());

        assertInstanceOf(LikePredicate.class, andExpressions.get(1));
        LikePredicate andPredicate = (LikePredicate) andExpressions.get(1);
        LiteralExpression andLiteralExpression = (LiteralExpression) andPredicate.getPattern();
        assertEquals(toUpperCase(andFilter.getStartWithIgnoreCase() + "%"), andLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, andPredicate.getMatchExpression());

        assertInstanceOf(LikePredicate.class, expressions.get(1));
        LikePredicate orPredicate = (LikePredicate) expressions.get(1);
        LiteralExpression orLiteralExpression = (LiteralExpression) orPredicate.getPattern();
        assertEquals(toUpperCase(orFilter.getStartWithIgnoreCase() + "%"), orLiteralExpression.getLiteral());
        assertInstanceOf(UpperFunction.class, orPredicate.getMatchExpression());
    }
}