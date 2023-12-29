package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.binark.querypredicate.filter.DateFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.BetweenPredicate;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateFilterPredicateBuilderTest {

  @Mock(answer = Answers.RETURNS_SELF)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  private DateFilterPredicateBuilder predicateBuilder = new DateFilterPredicateBuilder();

  @Test
  void buildPredicate_for_today() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsToday(true);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "today");

    assertNotNull(predicate);
    assertInstanceOf(BetweenPredicate.class, predicate);

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_not_for_today() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsToday(false);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "today");

    assertNotNull(predicate);
    assertInstanceOf(NegatedPredicateWrapper.class, predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildPredicate_for_tomorrow() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsTomorrow(true);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "tomorrow");

    assertNotNull(predicate);
    assertInstanceOf(BetweenPredicate.class, predicate);

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24) )), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24) )), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_not_for_tomorrow() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsTomorrow(false);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "tomorrow");

    assertNotNull(predicate);
    assertInstanceOf(NegatedPredicateWrapper.class, predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

    assertNotNull(negatedPredicateWrapper);
  }

  @Test
  void buildPredicate_for_yesterday() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsYesterday(true);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "yesterday");

    assertNotNull(predicate);
    assertInstanceOf(BetweenPredicate.class, predicate);

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate;

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24) )), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24) )), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_not_for_yesterday() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsYesterday(false);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "yesterday");

    assertNotNull(predicate);
    assertInstanceOf(NegatedPredicateWrapper.class, predicate);

    NegatedPredicateWrapper negatedPredicateWrapper = (NegatedPredicateWrapper) predicate;

    assertNotNull(negatedPredicateWrapper);
  }

  private Date atEndOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTime();
  }

  private Date atStartOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
}