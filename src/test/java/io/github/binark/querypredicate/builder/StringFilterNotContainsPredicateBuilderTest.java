package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringFilterNotContainsPredicateBuilderTest extends StringFilterPredicateBuilderTest {

    @Test
    void buildNotContainsPredicate() {
        StringFilter filter = new StringFilter();
        filter.setNotContains(VALUE);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    }

    @Test
    void buildAndNotContainsPredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setNotContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setNotContains(VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }

    @Test
    void buildOrNotContainsPredicate() {
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setNotContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setNotContains(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(OR, compoundPredicate.getOperator().name());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());

        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }

    @Test
    void buildFullNotContainsPredicate() {
        BaseStringFilter andFilter = new BaseStringFilter();
        andFilter.setNotContains("foo");
        BaseStringFilter orFilter = new BaseStringFilter();
        orFilter.setNotContains(OTHER_VALUE);
        StringFilter filter = new StringFilter();
        filter.setNotContains(VALUE);
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

        assertInstanceOf(NegatedPredicateWrapper.class, andExpressions.get(0));
        assertInstanceOf(NegatedPredicateWrapper.class, andExpressions.get(1));
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(1));
    }
}