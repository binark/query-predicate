package io.github.binark.querypredicate.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringFilterPredicateBuilderTest {

    protected static final String VALUE = "value";
    protected static final String OTHER_VALUE = "other_value";
    protected static final String EQUALS_VALUE = "equals";
    protected static final String FIELD_NAME = "field";
    protected static final String AND = "AND";
    protected static final String OR = "OR";
    @Mock(answer = Answers.RETURNS_SELF)
    protected Path path;
    @Mock(answer = Answers.RETURNS_MOCKS)
    protected SessionFactoryImpl sessionFactory;
    protected CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);
    protected StringFilterPredicateBuilder predicateBuilder = new StringFilterPredicateBuilder();

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(path.getJavaType()).thenReturn(String.class);
    }

    protected String toUpperCase(String value) {
        if (value != null) {
            return value.toUpperCase();
        }
        return value;
    }
}