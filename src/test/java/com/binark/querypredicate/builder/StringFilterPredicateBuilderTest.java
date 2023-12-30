package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.expression.function.UpperFunction;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringFilterPredicateBuilderTest {

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  private StringFilterPredicateBuilder predicateBuilder = new StringFilterPredicateBuilder();

  private static final String VALUE = "value";
  private static final String FIELD_NAME = "field";


  @Test
  void buildPredicate_Contains() {
    StringFilter filter = new StringFilter();
    filter.setContains(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);
    LikePredicate likePredicate = (LikePredicate) predicate;
    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals("%"+VALUE+"%", literalExpression.getLiteral());
    assertNull(likePredicate.getMatchExpression().toString());
  }

  @Test
  void buildPredicate_Not_Contains() {
    StringFilter filter = new StringFilter();
    filter.setNotContains(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildPredicate_Start_With() {
    StringFilter filter = new StringFilter();
    filter.setStartWith(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);
    LikePredicate likePredicate = (LikePredicate) predicate;
    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals(VALUE+"%", literalExpression.getLiteral());
    assertNull(likePredicate.getMatchExpression().toString());
  }

  @Test
  void buildPredicate_End_With() {
    StringFilter filter = new StringFilter();
    filter.setEndWith(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);
    LikePredicate likePredicate = (LikePredicate) predicate;
    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals("%"+VALUE, literalExpression.getLiteral());
    assertNull(likePredicate.getMatchExpression().toString());
  }

  @Test
  void buildPredicate_Contains_Ignore_Case() {
    StringFilter filter = new StringFilter();
    filter.setContainsIgnoreCase(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);

    LikePredicate likePredicate = (LikePredicate) predicate;
    assertNotNull(likePredicate.getMatchExpression().toString());
    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());

    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals("%"+VALUE.toUpperCase()+"%", literalExpression.getLiteral());
  }

  @Test
  void buildPredicate_Not_Contains_Ignore_Case() {
    StringFilter filter = new StringFilter();
    filter.setNotContainsIgnoreCase(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(NegatedPredicateWrapper.class, predicate);
    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildPredicate_Start_With_Ignore_Case() {
    StringFilter filter = new StringFilter();
    filter.setStartWithIgnoreCase(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);

    LikePredicate likePredicate = (LikePredicate) predicate;
    assertNotNull(likePredicate.getMatchExpression().toString());
    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());

    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals(VALUE.toUpperCase()+"%", literalExpression.getLiteral());
  }

  @Test
  void buildPredicate_End_With_Ignore_Case() {
    StringFilter filter = new StringFilter();
    filter.setEndWithIgnoreCase(VALUE);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
        FIELD_NAME);

    assertNotNull(predicate);
    assertInstanceOf(LikePredicate.class, predicate);

    LikePredicate likePredicate = (LikePredicate) predicate;
    assertNotNull(likePredicate.getMatchExpression().toString());
    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());

    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
    assertEquals("%"+VALUE.toUpperCase(), literalExpression.getLiteral());
  }
}