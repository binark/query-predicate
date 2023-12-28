package com.binark.querypredicate.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.binark.querypredicate.filter.LocalDateFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
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
class LocalDateFilterPredicateBuilderTest {

  @Mock(answer = Answers.RETURNS_MOCKS)
  private Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private SessionFactoryImpl sessionFactory;

  private CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

  private LocalDateFilterPredicateBuilder predicateBuilder = new LocalDateFilterPredicateBuilder();

  @Test
  void buildPredicate_for_today() {
    LocalDateFilter localDateFilter = new LocalDateFilter();
    localDateFilter.setIsToday(true);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
        "today");

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
    LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

    assertEquals(LocalDate.now().atStartOfDay().toLocalDate(), lowerBound.getLiteral());
    assertEquals(LocalDate.now().atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_for_tomorrow() {
    LocalDateFilter localDateFilter = new LocalDateFilter();
    localDateFilter.setIsTomorrow(true);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
        "tomorrow");

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
    LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

    assertEquals(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
    assertEquals(LocalDate.now().plusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
  }

  @Test
  void buildPredicate_for_yesterday() {
    LocalDateFilter localDateFilter = new LocalDateFilter();
    localDateFilter.setIsYesterday(true);

    Predicate predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, localDateFilter,
        "yesterday");

    BetweenPredicate betweenPredicate = (BetweenPredicate) predicate.getExpressions().stream()
        .filter(item -> item instanceof BetweenPredicate).findFirst().get();

    assertNotNull(betweenPredicate);

    LiteralExpression<LocalDate> lowerBound = (LiteralExpression<LocalDate>) betweenPredicate.getLowerBound();
    LiteralExpression<LocalDate> upperBound = (LiteralExpression<LocalDate>) betweenPredicate.getUpperBound();

    assertEquals(LocalDate.now().minusDays(1).atStartOfDay().toLocalDate(), lowerBound.getLiteral());
    assertEquals(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).toLocalDate(), upperBound.getLiteral());
  }
}