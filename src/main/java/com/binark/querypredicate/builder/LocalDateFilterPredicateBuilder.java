package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.LocalDateFilter;
import com.binark.querypredicate.filter.Range;
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
    Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);

    if (filter.getIsToday() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now(), fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsToday(), builder, predicate, localDateBetweenPredicate);
    }

    if (filter.getIsTomorrow() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now().plusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsTomorrow(), builder, predicate, localDateBetweenPredicate);
    }

    if (filter.getIsYesterday() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now().minusDays(1), fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsYesterday(), builder, predicate, localDateBetweenPredicate);
    }

    return predicate;
  }

  private Predicate computeDateBetweenPredicate(Boolean filterRule, CriteriaBuilder builder, Predicate basePredicate, Predicate betweenPredicate) {
    if (Boolean.TRUE.equals(filterRule)) {
      return builder.or(basePredicate, betweenPredicate);
    }
    return builder.or(basePredicate, betweenPredicate.not());
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
}
