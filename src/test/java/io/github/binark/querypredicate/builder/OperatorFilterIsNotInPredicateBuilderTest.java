package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestBaseFilter;
import io.github.binark.querypredicate.testdouble.TestObject;
import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

class OperatorFilterIsNotInPredicateBuilderTest extends OperatorFilterPredicateBuilderTestInit {
    public static final List<TestObject> FOO_BAR_LIST = List.of(new TestObject(), new TestObject());
    public static final List<TestObject> JOHN_DOE_LIST = List.of(new TestObject(), new TestObject());

    @Mock(answer = Answers.RETURNS_SELF)
    Predicate inPredicate;
    @Mock(answer = Answers.RETURNS_SELF)
    Predicate andInPredicate;
    @Mock(answer = Answers.RETURNS_SELF)
    Predicate orInPredicate;

    @Test
    void testBuildSimpleAndNotInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setAnd(andFilter);
        when(path.in(anyCollection())).thenReturn(inPredicate);

        predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);
        verify(path).get(FIELD_NAME);
        verify(path).in(andFilter.getIsNotIn());
        verify(inPredicate).not();
    }

    @Test
    void testBuildIsNotInPlusAndNotInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setAnd(andFilter);
        filter.setIsNotIn(JOHN_DOE_LIST);
        when(path.in(anyCollection())).thenReturn(inPredicate, andInPredicate);

        Predicate predicate = predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.AND, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(inPredicate, expressions.get(0));
        assertEquals(andInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(filter.getIsNotIn());
        verify(path).in(andFilter.getIsNotIn());
        verify(inPredicate).not();
        verify(andInPredicate).not();
    }

    @Test
    void testBuildSimpleOrNotInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        when(path.in(anyCollection())).thenReturn(inPredicate);

        predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        verify(path).get(FIELD_NAME);
        verify(path).in(orFilter.getIsNotIn());
        verify(inPredicate).not();
    }

    @Test
    void testBuildIsInPlusOrInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        filter.setIsNotIn(JOHN_DOE_LIST);
        when(path.in(anyCollection())).thenReturn(inPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(inPredicate, expressions.get(0));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(filter.getIsNotIn());
        verify(path).in(orFilter.getIsNotIn());
        verify(inPredicate).not();
        verify(orInPredicate).not();
    }

    @Test
    void testBuildAndNotInPlusOrNotInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsNotIn(JOHN_DOE_LIST);
        filter.setAnd(andFilter);
        when(path.in(anyCollection())).thenReturn(andInPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertEquals(andInPredicate, expressions.get(0));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(2)).get(FIELD_NAME);
        verify(path).in(andFilter.getIsNotIn());
        verify(path).in(orFilter.getIsNotIn());
        verify(andInPredicate).not();
        verify(orInPredicate).not();
    }

    @Test
    void testBuildIsNotInPlusAndNotInPlusOrNotInPredicate() {
        TestOperatorFilter filter = new TestOperatorFilter();
        TestBaseFilter orFilter = new TestBaseFilter();
        orFilter.setIsNotIn(FOO_BAR_LIST);
        filter.setOr(orFilter);
        TestBaseFilter andFilter = new TestBaseFilter();
        andFilter.setIsNotIn(JOHN_DOE_LIST);
        filter.setAnd(andFilter);
        filter.setIsNotIn(List.of(new TestObject(), new TestObject()));
        when(path.in(anyCollection())).thenReturn(inPredicate, andInPredicate, orInPredicate);

        Predicate predicate = predicateBuilder.buildIsNotInPredicate(filter, criteriaBuilder, path, FIELD_NAME);

        assertInstanceOf(CompoundPredicate.class, predicate);
        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(Predicate.BooleanOperator.OR, compoundPredicate.getOperator());
        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertEquals(2, expressions.size());
        assertInstanceOf(CompoundPredicate.class, expressions.get(0));
        CompoundPredicate andCompoundPredicate = (CompoundPredicate) expressions.get(0);
        assertEquals(Predicate.BooleanOperator.AND, andCompoundPredicate.getOperator());
        List<Expression<Boolean>> andExpressions = andCompoundPredicate.getExpressions();
        assertEquals(inPredicate, andExpressions.get(0));
        assertEquals(andInPredicate, andExpressions.get(1));
        assertEquals(orInPredicate, expressions.get(1));
        verify(path, times(3)).get(FIELD_NAME);
        verify(path).in(filter.getIsNotIn());
        verify(path).in(andFilter.getIsNotIn());
        verify(path).in(orFilter.getIsNotIn());
        verify(inPredicate).not();
        verify(andInPredicate).not();
        verify(orInPredicate).not();
    }
}