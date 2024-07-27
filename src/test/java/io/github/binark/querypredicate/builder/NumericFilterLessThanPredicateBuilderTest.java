package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.NumericFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NumericFilterLessThanPredicateBuilderTest {

    public static final String FIELD_NAME = "fieldName";
    public static final int VALUE = 123;
    public static final int OTHER_VALUE = 456;
    public static final String AND = "AND";
    public static final String OR = "OR";

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private NumericFilterPredicateBuilder predicateBuilder;

    @Mock(answer = Answers.RETURNS_SELF)
    private Path path;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;

    @InjectMocks
    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    private NumericFilter filter;

    @BeforeEach
    void setUp() {
        Mockito.when(path.getJavaType()).thenReturn(Number.class);
        filter = Mockito.mock(NumericFilter.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void buildNumericPredicate_Is_LessThan() {
        filter.setIsLessThan(VALUE);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildNumericPredicate_Is_LessThan_With_Not_Null_Predicate() {
        filter.setNull(false);
        filter.setIsLessThan(VALUE);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());
        assertInstanceOf(NegatedPredicateWrapper.class, expressions.get(0));

        ComparisonPredicate lessThanComparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN, lessThanComparisonPredicate.getComparisonOperator());

        LiteralExpression lessThanLiteralExpression = (LiteralExpression) lessThanComparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, lessThanLiteralExpression.getLiteral());
    }

    @Test
    void buildNumericPredicate_Or_Is_LessThan() {
        NumericFilter orFilter = Mockito.mock(NumericFilter.class, Mockito.CALLS_REAL_METHODS);
        orFilter.setIsLessThan(VALUE);
        filter.setOr(orFilter);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(ComparisonPredicate.class, predicate);

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicate;
        assertEquals(ComparisonOperator.LESS_THAN, comparisonPredicate.getComparisonOperator());

        LiteralExpression literalExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, literalExpression.getLiteral());
    }

    @Test
    void buildNumericPredicate_And_With_Normal_Is_LessThan() {
        NumericFilter andFilter = Mockito.mock(NumericFilter.class, Mockito.CALLS_REAL_METHODS);
        andFilter.setIsLessThan(VALUE);
        filter.setIsLessThan(OTHER_VALUE);
        filter.setAnd(andFilter);

        Predicate predicate = predicateBuilder.buildNumericPredicate(path, criteriaBuilder,
                filter, FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        ComparisonPredicate normalComparisonPredicate = (ComparisonPredicate) expressions.get(0);
        assertEquals(ComparisonOperator.LESS_THAN, normalComparisonPredicate.getComparisonOperator());

        LiteralExpression normalLiteralExpression = (LiteralExpression) normalComparisonPredicate.getRightHandOperand();
        assertEquals(OTHER_VALUE, normalLiteralExpression.getLiteral());

        ComparisonPredicate andComparisonPredicate = (ComparisonPredicate) expressions.get(1);
        assertEquals(ComparisonOperator.LESS_THAN, andComparisonPredicate.getComparisonOperator());

        LiteralExpression andLiteralExpression = (LiteralExpression) andComparisonPredicate.getRightHandOperand();
        assertEquals(VALUE, andLiteralExpression.getLiteral());
    }

}