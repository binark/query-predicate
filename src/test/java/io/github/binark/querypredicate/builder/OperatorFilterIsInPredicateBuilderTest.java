package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestObject;
import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

class OperatorFilterIsInPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {
    public static final List<TestObject> FOO_BAR_LIST = List.of(new TestObject(), new TestObject());
    public static final List<TestObject> JOHN_DOE_LIST = List.of(new TestObject(), new TestObject());

    @Test
    void testBuildSimpleAndInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsIn(FOO_BAR_LIST);
        filter.setAnd(andFilter);

        predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);
        verify(path).get(FIELD_NAME);
        verify(path).in(andFilter.getIsIn());
    }

    @Test
    void testBuildIsInPlusAndInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsIn(FOO_BAR_LIST);
        filter.setAnd(andFilter);
        filter.setIsIn(JOHN_DOE_LIST);
        Predicate isInPredicate = mock(Predicate.class);
        Predicate andInPredicate = mock(Predicate.class);
        when(path.in(anyCollection())).thenReturn(isInPredicate, andInPredicate);

        Predicate predicate = predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(isInPredicate, expressions.get(0));
        assertEquals(andInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(filter.getIsIn());
        verify(path).in(andFilter.getIsIn());
    }

    @Test
    void testBuildSimpleOrInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsIn(FOO_BAR_LIST);
        filter.setOr(orFilter);

        predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        verify(path).get(FIELD_NAME);
        verify(path).in(orFilter.getIsIn());
    }

    @Test
    void testBuildIsInPlusOrInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        filter.setIsIn(JOHN_DOE_LIST);
        Predicate isInPredicate = mock(Predicate.class);
        Predicate orInPredicate = mock(Predicate.class);
        when(path.in(anyCollection())).thenReturn(isInPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(isInPredicate, expressions.get(0));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(filter.getIsIn());
        verify(path).in(orFilter.getIsIn());
    }

    @Test
    void testBuildAndInPlusOrInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsIn(JOHN_DOE_LIST);
        filter.setAnd(andFilter);
        Predicate andInPredicate = mock(Predicate.class);
        Predicate orInPredicate = mock(Predicate.class);
        when(path.in(anyCollection())).thenReturn(andInPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(andInPredicate, expressions.get(0));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(andFilter.getIsIn());
        verify(path).in(orFilter.getIsIn());
    }

    @Test
    void testBuildIsInPlusAndInPlusOrInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsIn(JOHN_DOE_LIST);
        filter.setAnd(andFilter);
        filter.setIsIn(List.of(new TestObject(), new TestObject()));
        Predicate isInPredicate = mock(Predicate.class);
        Predicate andInPredicate = mock(Predicate.class);
        Predicate orInPredicate = mock(Predicate.class);
        when(path.in(anyCollection())).thenReturn(isInPredicate, andInPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andCompoundPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andCompoundPredicate.getOperator());
        List<Expression<Boolean>> andExpressions = andCompoundPredicate.getExpressions();
        assertEquals(isInPredicate, andExpressions.get(0));
        assertEquals(andInPredicate, andExpressions.get(1));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(3)).get(FIELD_NAME);
        verify(path).in(filter.getIsIn());
        verify(path).in(andFilter.getIsIn());
        verify(path).in(orFilter.getIsIn());
    }
}