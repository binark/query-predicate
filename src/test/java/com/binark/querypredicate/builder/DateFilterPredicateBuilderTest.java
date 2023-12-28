package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateFilterPredicateBuilderTest {

  @Mock(answer = Answers.RETURNS_MOCKS)
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

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date()), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date()), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_for_tomorrow() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsTomorrow(true);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "tomorrow");

    assertNotNull(predicate);

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24) )), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date(new Date().getTime() + (1000 * 60 * 60 * 24) )), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_for_yesterday() {
    DateFilter dateFilter = new DateFilter();
    dateFilter.setIsYesterday(true);
    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, dateFilter,
        "yesterday");

    assertNotNull(predicate);

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<Date> lowerBound = (LiteralExpression<Date>) betweenPredicate.getLowerBound();
    LiteralExpression<Date> upperBound = (LiteralExpression<Date>) betweenPredicate.getUpperBound();

    assertEquals(atStartOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24) )), lowerBound.getLiteral());
    assertEquals(atEndOfDay(new Date(new Date().getTime() - (1000 * 60 * 60 * 24) )), upperBound.getLiteral());
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