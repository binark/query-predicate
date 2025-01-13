package io.github.binark.querypredicate.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

class BaseStringFilterPredicateBuilderTest {

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
    protected BaseStringFilterPredicateBuilder predicateBuilder = new BaseStringFilterPredicateBuilder();

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(path.getJavaType()).thenReturn(String.class);
  }


//  @Test
//  void buildPredicate_Contains() {
//    StringFilter filter = new StringFilter();
//    filter.setContains(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals("%"+VALUE+"%", literalExpression.getLiteral());
//    assertNull(likePredicate.getMatchExpression().toString());
//  }
//
//  @Test
//  void buildPredicate_Not_Contains() {
//    StringFilter filter = new StringFilter();
//    filter.setNotContains(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(NegatedPredicateWrapper.class, predicate);
//    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
//    assertNotNull(negatedPredicateWrapper);
//  }
//
//  @Test
//  void buildPredicate_Start_With() {
//    StringFilter filter = new StringFilter();
//    filter.setStartWith(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals(VALUE+"%", literalExpression.getLiteral());
//    assertNull(likePredicate.getMatchExpression().toString());
//  }
//
//  @Test
//  void buildPredicate_End_With() {
//    StringFilter filter = new StringFilter();
//    filter.setEndWith(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals("%"+VALUE, literalExpression.getLiteral());
//    assertNull(likePredicate.getMatchExpression().toString());
//  }
//
//  @Test
//  void buildPredicate_Contains_Ignore_Case() {
//    StringFilter filter = new StringFilter();
//    filter.setContainsIgnoreCase(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    assertNotNull(likePredicate.getMatchExpression().toString());
//    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());
//
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals("%"+VALUE.toUpperCase()+"%", literalExpression.getLiteral());
//  }
//
//  @Test
//  void buildPredicate_Not_Contains_Ignore_Case() {
//    StringFilter filter = new StringFilter();
//    filter.setNotContainsIgnoreCase(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(NegatedPredicateWrapper.class, predicate);
//    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;
//    assertNotNull(negatedPredicateWrapper);
//  }
//
//  @Test
//  void buildPredicate_Start_With_Ignore_Case() {
//    StringFilter filter = new StringFilter();
//    filter.setStartWithIgnoreCase(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    assertNotNull(likePredicate.getMatchExpression().toString());
//    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());
//
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals(VALUE.toUpperCase()+"%", literalExpression.getLiteral());
//  }
//
//  @Test
//  void buildPredicate_End_With_Ignore_Case() {
//    StringFilter filter = new StringFilter();
//    filter.setEndWithIgnoreCase(VALUE);
//
//    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, filter,
//        FIELD_NAME);
//
//    assertNotNull(predicate);
//    assertInstanceOf(LikePredicate.class, predicate);
//
//    LikePredicate likePredicate = (LikePredicate) predicate;
//    assertNotNull(likePredicate.getMatchExpression().toString());
//    assertInstanceOf(UpperFunction.class, likePredicate.getMatchExpression());
//
//    LiteralExpression literalExpression = (LiteralExpression) likePredicate.getPattern();
//    assertEquals("%"+VALUE.toUpperCase(), literalExpression.getLiteral());
//  }
}