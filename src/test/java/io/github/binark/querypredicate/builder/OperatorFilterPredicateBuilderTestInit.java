package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.testdouble.TestOperatorFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperatorFilterPredicateBuilderTestInit {
    protected static final String FIELD_NAME = "fieldName";
    @Mock(answer = Answers.RETURNS_SELF)
    protected Path path;

    @Mock(answer = Answers.RETURNS_MOCKS)
    protected SessionFactoryImpl sessionFactory;

    protected CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    protected OperatorFilterPredicateBuilder<TestOperatorFilter> predicateBuilder;
}