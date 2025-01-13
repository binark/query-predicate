package io.github.binark.querypredicate.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.mockito.Answers;
import org.mockito.Mock;

class BaseLocalDateFilterPredicateBuilderTest {

    protected static final String AND = "AND";
    protected static final String OR = "OR";

  @Mock(answer = Answers.RETURNS_SELF)
  protected Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  protected SessionFactoryImpl sessionFactory;

    protected CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    protected BaseLocalDateFilterPredicateBuilder predicateBuilder = new BaseLocalDateFilterPredicateBuilder();
}