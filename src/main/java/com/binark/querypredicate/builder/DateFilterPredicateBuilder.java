package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.DateFilter;
import com.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The predicate builder for the {@link DateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DateFilterPredicateBuilder extends ComparableFilterPredicateBuilder<DateFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, DateFilter filter,
      String fieldName) {
    List<Predicate> predicates = super.buildComparablePredicate(path, builder, filter, fieldName);

    if (filter.getIsToday() != null) {
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      computeDateBetweenPredicate(filter.getIsToday(), predicates, betweenPredicate);
    }

    if (filter.getIsTomorrow() != null) {
      Date tomorrow = new Date( new Date().getTime() + (1000 * 60 * 60 * 24) );
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      computeDateBetweenPredicate(filter.getIsTomorrow(), predicates, betweenPredicate);
    }

    if (filter.getIsYesterday() != null) {
      Date yesterday = new Date( new Date().getTime() - (1000 * 60 * 60 * 24) );
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      computeDateBetweenPredicate(filter.getIsYesterday(), predicates, betweenPredicate);
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

  private Predicate getDateBetweenPredicate(Path path, CriteriaBuilder builder, Date date, String fieldName) {
    Range<Date> range = new Range<>();
    range.setStart(atStartOfDay(date));
    range.setEnd(atEndOfDay(date));
    return getBetweenPredicate(path, builder, range, fieldName);
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
