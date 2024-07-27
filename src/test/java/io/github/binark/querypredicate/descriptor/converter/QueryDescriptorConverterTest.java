package io.github.binark.querypredicate.descriptor.converter;


import io.github.binark.querypredicate.descriptor.FilterAndSubQueryDescriptor;
import io.github.binark.querypredicate.descriptor.SimpleQueryDescriptor;
import io.github.binark.querypredicate.filter.DateFilter;
import io.github.binark.querypredicate.filter.LocalDateFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate.ComparisonOperator;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QueryDescriptorConverterTest {

    public static final String STRING_VALUE = "stringValue";
    @Mock(answer = Answers.RETURNS_MOCKS)
    private Root root;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;

    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    QueryDescriptorConverter queryDescriptorConverter = new QueryDescriptorConverter();

    @Test
    public void interpretDescriptor_With_One_String_Filter_Field() {
        StringFilter id = new StringFilter();
        id.setStartWith(STRING_VALUE);
        SimpleQueryDescriptor simpleQueryDescriptor = new SimpleQueryDescriptor();
        simpleQueryDescriptor.setId(id);

        List<Predicate> predicates = queryDescriptorConverter.convert(root, criteriaBuilder, simpleQueryDescriptor);

        assertNotNull(predicates);
        assertEquals(1, predicates.size());
        assertInstanceOf(LikePredicate.class, predicates.get(0));

        LikePredicate likePredicate = (LikePredicate) predicates.get(0);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();

        assertEquals(STRING_VALUE+"%", literalExpression.getLiteral());
    }

    @Test
    public void interpretDescriptor_With_String_Filter_And_Date_Filter_Fields() {
        root = Mockito.mock(Root.class, Mockito.RETURNS_SELF);
        Mockito.when(root.getJavaType()).thenReturn(LocalDateTime.class);
        Date today = new Date();
        StringFilter id = new StringFilter();
        id.setContains(STRING_VALUE);
        DateFilter birthday = new DateFilter();
        birthday.setIsGreaterThan(today);
        LocalDateFilter createdAt = new LocalDateFilter();
        createdAt.setIsYesterday(true);
        SimpleQueryDescriptor simpleQueryDescriptor = new SimpleQueryDescriptor();
        simpleQueryDescriptor.setId(id);
        simpleQueryDescriptor.setBirthday(birthday);
        simpleQueryDescriptor.setCreatedAt(createdAt);

        List<Predicate> predicates = queryDescriptorConverter.convert(root, criteriaBuilder, simpleQueryDescriptor);

        assertNotNull(predicates);
        assertEquals(3, predicates.size());

        LikePredicate likePredicate = (LikePredicate) predicates.stream().filter(p -> p instanceof LikePredicate).findFirst().get();

        assertNotNull(likePredicate);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%"+STRING_VALUE+"%", literalExpression.getLiteral());

        ComparisonPredicate comparisonPredicate = (ComparisonPredicate) predicates.stream().filter(p -> p instanceof ComparisonPredicate).findFirst().get();
        assertNotNull(comparisonPredicate);
        assertEquals(ComparisonOperator.GREATER_THAN, comparisonPredicate.getComparisonOperator());
        LiteralExpression comparisonExpression = (LiteralExpression) comparisonPredicate.getRightHandOperand();
        assertInstanceOf(Date.class, comparisonExpression.getLiteral());
        assertEquals(today, comparisonExpression.getLiteral());

        BetweenPredicate betweenPredicate = (BetweenPredicate) predicates.stream().filter(p -> p instanceof BetweenPredicate).findFirst().get();
        assertNotNull(likePredicate);
        LiteralExpression lowerBound = (LiteralExpression) betweenPredicate.getLowerBound();
        LiteralExpression upperBound = (LiteralExpression) betweenPredicate.getUpperBound();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        assertEquals(yesterday.atStartOfDay().toLocalDate(), lowerBound.getLiteral());
        assertEquals(yesterday.atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());

    }

    @Test
    public void interpretDescriptor_with_filter_fields_and_sub_descriptor() {
        StringFilter id = new StringFilter();
        id.setNotContains("nonId");
        SimpleQueryDescriptor subField = new SimpleQueryDescriptor();
        subField.setId(id);
        StringFilter firstName = new StringFilter();
        firstName.setContainsIgnoreCase(STRING_VALUE);
        FilterAndSubQueryDescriptor queryDescriptor = new FilterAndSubQueryDescriptor();
        queryDescriptor.setSubField(subField);
        queryDescriptor.setFirstName(firstName);

        List<Predicate> predicates = queryDescriptorConverter.convert(root, criteriaBuilder, queryDescriptor);

        assertNotNull(predicates);
        assertEquals(2, predicates.size());

        NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicates.stream().filter(p -> p instanceof NegatedPredicateWrapper).findFirst().get();
        assertNotNull(negatedPredicateWrapper);

        LikePredicate likePredicate = (LikePredicate) predicates.stream().filter(p -> p instanceof LikePredicate).findFirst().get();
        assertNotNull(likePredicate);
        LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
        assertEquals("%"+STRING_VALUE.toUpperCase()+"%", literalExpression.getLiteral());
    }
}