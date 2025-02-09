package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseComparableFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BaseComparableBetweenFilterPredicateBuilderTest {

    public static final String FIELD_NAME = "fieldName";
    public static final Instant VALUE = Instant.now();
    public static final Instant OTHER_VALUE = Instant.MIN;
    public static final String AND = "AND";
    public static final String OR = "OR";

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private BaseComparableFilterPredicateBuilder predicateBuilder;

    @Mock(answer = Answers.RETURNS_SELF)
    private Path path;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;

    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    private BaseComparableFilter filter;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(path.getJavaType()).thenReturn(Instant.class);
        filter = mock(BaseComparableFilter.class, CALLS_REAL_METHODS);
    }

    @Test
    void buildIsBetweenPredicate() {
        Range<Instant> range = new Range<>();
        range.setStart(OTHER_VALUE);
        range.setEnd(VALUE);
        filter.setIsBetween(range);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(BetweenPredicate.class, predicate);

        BetweenPredicate<Instant> betweenPredicate = (BetweenPredicate<Instant>) predicate;
        LiteralExpression<Instant> lowerExpression = (LiteralExpression) betweenPredicate.getLowerBound();

        assertNotNull(lowerExpression);
        assertEquals(OTHER_VALUE, lowerExpression.getLiteral());

        LiteralExpression<Instant> upperExpression = (LiteralExpression) betweenPredicate.getUpperBound();
        assertNotNull(upperExpression);
        assertEquals(VALUE, upperExpression.getLiteral());
    }

    @Test
    void buildCombinedIsEqualsAndIsBetweenPredicates() {
        Range<Instant> range = new Range<>();
        range.setStart(OTHER_VALUE);
        range.setEnd(VALUE);
        filter.setIsEquals(Instant.now());
        filter.setIsBetween(range);

        Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
                                                              FIELD_NAME);

        assertNotNull(predicate);
        assertInstanceOf(CompoundPredicate.class, predicate);

        CompoundPredicate compoundPredicate = (CompoundPredicate) predicate;
        assertEquals(AND, compoundPredicate.getOperator().name());

        List<Expression<Boolean>> expressions = compoundPredicate.getExpressions();
        assertNotNull(expressions);
        assertEquals(2, expressions.size());

        BetweenPredicate<Instant> betweenPredicate = (BetweenPredicate<Instant>) expressions.get(1);
        LiteralExpression<Instant> lowerExpression = (LiteralExpression) betweenPredicate.getLowerBound();

        assertNotNull(lowerExpression);
        assertEquals(OTHER_VALUE, lowerExpression.getLiteral());

        LiteralExpression<Instant> upperExpression = (LiteralExpression) betweenPredicate.getUpperBound();
        assertNotNull(upperExpression);
        assertEquals(VALUE, upperExpression.getLiteral());
    }
}