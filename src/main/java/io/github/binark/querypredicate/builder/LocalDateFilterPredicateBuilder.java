package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.LocalDateFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The predicate builder for the {@link LocalDateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LocalDateFilterPredicateBuilder extends ComparableFilterPredicateBuilder<LocalDateFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, LocalDateFilter filter,
      String fieldName) {
    Predicate predicate = super.buildComparablePredicate(path, builder, filter, fieldName);

    Boolean andToday = getAndToday(filter);
    if (andToday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              LocalDate.now(), fieldName);
      predicate = computeDateBetweenPredicate(andToday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orToday = getOrToday(filter);
    if (orToday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              LocalDate.now(), fieldName);
      predicate = computeDateBetweenPredicate(orToday, predicate, localDateBetweenPredicate, builder, true);
    }

    Boolean andTomorrow = getAndTomorrow(filter);
    if (andTomorrow != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              LocalDate.now().plusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(andTomorrow, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orTomorrow = getOrTomorrow(filter);
    if (orTomorrow != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              LocalDate.now().plusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(orTomorrow, predicate, localDateBetweenPredicate, builder, true);
    }

    Boolean andYesterday = getAndYesterday(filter);
    if (andYesterday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now().minusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(andYesterday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orYesterday = getOrYesterday(filter);
    if (orYesterday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              LocalDate.now().minusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(orYesterday, predicate, localDateBetweenPredicate, builder, true);
    }

    return predicate;
  }

  private Predicate computeDateBetweenPredicate(Boolean filterRule, Predicate basePredicate, Predicate temporaryPredicate, CriteriaBuilder builder, boolean isOrOperator) {
    if (!Boolean.TRUE.equals(filterRule)) {
      temporaryPredicate = temporaryPredicate.not();
    }
    if (basePredicate == null) {
      return temporaryPredicate;
    }
    if (isOrOperator) {
      return builder.or(basePredicate, temporaryPredicate);
    }
    return builder.and(basePredicate, temporaryPredicate);
  }

  private Predicate getLocalDateBetweenPredicate(Path path, CriteriaBuilder builder, LocalDate localDate, String fieldName) {
    Range<LocalDate> range = new Range<>();
    range.setStart(atStartOfDay(localDate));
    range.setEnd(atEndOfDay(localDate));
    return getBetweenPredicate(path, builder, range, fieldName);
  }

  private LocalDate atStartOfDay(LocalDate localDate) {
    return localDate.atStartOfDay().toLocalDate();
  }

  private LocalDate atEndOfDay(LocalDate localDate) {
    return localDate.atTime(LocalTime.MAX).toLocalDate();
  }

  private Boolean getAndToday(LocalDateFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsToday();
    }
    return null;
  }

  private Boolean getOrToday(LocalDateFilter filter) {
    Boolean value = filter.getIsToday();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsToday();
    }
    return value;
  }

  private Boolean getAndTomorrow(LocalDateFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsTomorrow();
    }
    return null;
  }

  private Boolean getOrTomorrow(LocalDateFilter filter) {
    Boolean value = filter.getIsTomorrow();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsTomorrow();
    }
    return value;
  }

  private Boolean getAndYesterday(LocalDateFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsYesterday();
    }
    return null;
  }

  private Boolean getOrYesterday(LocalDateFilter filter) {
    Boolean value = filter.getIsYesterday();
    if (value == null && filter.getOr() != null) {
      value = filter.getOr().getIsYesterday();
    }
    return value;
  }
}
