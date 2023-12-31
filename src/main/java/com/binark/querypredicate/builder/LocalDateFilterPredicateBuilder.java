package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.LocalDateFilter;
import com.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The predicate builder for the {@link LocalDateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LocalDateFilterPredicateBuilder extends ComparableFilterPredicateBuilder<LocalDateFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, LocalDateFilter filter,
      String fieldName) {
    List<Predicate> predicates = super.buildComparablePredicate(path, builder, filter, fieldName);

    if (filter.getIsToday() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now(), fieldName);
      computeDateBetweenPredicate(filter.getIsToday(), predicates, localDateBetweenPredicate);
    }

    if (filter.getIsTomorrow() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now().plusDays(1), fieldName);
      computeDateBetweenPredicate(filter.getIsTomorrow(), predicates, localDateBetweenPredicate);
    }

    if (filter.getIsYesterday() != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
          LocalDate.now().minusDays(1), fieldName);
      computeDateBetweenPredicate(filter.getIsYesterday(), predicates, localDateBetweenPredicate);
    }

    return predicates.size() == 1 ? predicates.get(0) : builder.or(predicates.toArray(new Predicate[0]));
  }

  private void computeDateBetweenPredicate(Boolean filterRule, List<Predicate> basePredicates, Predicate betweenPredicate) {
    if (Boolean.TRUE.equals(filterRule)) {
      basePredicates.add(betweenPredicate);
    } else {
      basePredicates.add(betweenPredicate.not());
    }
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
