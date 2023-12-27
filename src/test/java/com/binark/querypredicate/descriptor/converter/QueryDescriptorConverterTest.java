package com.binark.querypredicate.descriptor.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.binark.querypredicate.descriptor.FilterAndSubQueryDescriptor;
import com.binark.querypredicate.descriptor.SimpleQueryDescriptor;
import com.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QueryDescriptorConverterTest {

    @Mock(answer = Answers.RETURNS_MOCKS)
    private Root root;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private SessionFactoryImpl sessionFactory;

    private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    QueryDescriptorConverter queryDescriptorConverter = new QueryDescriptorConverter();

    @Test
    public void interpretDescriptor_with_filter_fields_and_sub_descriptor() {
        StringFilter id = new StringFilter();
        id.setStartWith("myId");
        SimpleQueryDescriptor subField = new SimpleQueryDescriptor();
        subField.setId(id);
        StringFilter firstName = new StringFilter();
        firstName.setContainsIgnoreCase("first");
        FilterAndSubQueryDescriptor queryDescriptor = new FilterAndSubQueryDescriptor();
        queryDescriptor.setSubField(subField);
        queryDescriptor.setFirstName(firstName);

        List<Predicate> predicates = queryDescriptorConverter.convert(root, criteriaBuilder, queryDescriptor);

        assertNotNull(predicates);
        assertEquals(2, predicates.size());
    }
}