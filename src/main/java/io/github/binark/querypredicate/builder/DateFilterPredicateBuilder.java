package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.DateFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.Calendar;
import java.util.Date;

/**
 * The predicate builder for the {@link DateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DateFilterPredicateBuilder extends ComparableFilterPredicateBuilder<DateFilter>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, DateFilter filter,
      String fieldName) {
    Predicate predicate = super.buildComparablePredicate(path, builder, filter, fieldName);

    Boolean andToday = getAndToday(filter);
    if (andToday != null) {
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsToday(), predicate, betweenPredicate, builder, false);
    }

    Boolean orToday = getOrToday(filter);
    if (orToday != null) {
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsToday(), predicate, betweenPredicate, builder, true);
    }

    Boolean andTomorrow = getAndTomorrow(filter);
    if (andTomorrow != null) {
      Date tomorrow = new Date( new Date().getTime() + (1000 * 60 * 60 * 24) );
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsTomorrow(), predicate, betweenPredicate, builder, false);
    }

    Boolean orTomorrow = getOrTomorrow(filter);
    if (orTomorrow != null) {
      Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsTomorrow(), predicate, betweenPredicate, builder, true);
    }

    Boolean andYesterday = getAndYesterday(filter);
    if (andYesterday != null) {
      Date yesterday = new Date( new Date().getTime() - (1000 * 60 * 60 * 24) );
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsYesterday(), predicate, betweenPredicate, builder, false);
    }

    Boolean orYesterday = getOrYesterday(filter);
    if (orYesterday != null) {
      Date yesterday = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));
      Predicate betweenPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      predicate = computeDateBetweenPredicate(filter.getIsYesterday(), predicate, betweenPredicate, builder, true);
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

  private Boolean getAndToday(DateFilter filter) {
    Boolean value = filter.getIsToday();
    if (value == null && filter.getAnd() != null) {
      value = filter.getAnd().getIsToday();
    }
    return value;
  }

  private Boolean getOrToday(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsToday();
    }
    return null;
  }

  private Boolean getAndTomorrow(DateFilter filter) {
    Boolean value = filter.getIsTomorrow();
    if (value == null && filter.getAnd() != null) {
      value = filter.getAnd().getIsTomorrow();
    }
    return value;
  }

  private Boolean getOrTomorrow(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsTomorrow();
    }
    return null;
  }

  private Boolean getAndYesterday(DateFilter filter) {
    Boolean value = filter.getIsYesterday();
    if (value == null && filter.getAnd() != null) {
      value = filter.getAnd().getIsYesterday();
    }
    return value;
  }

  private Boolean getOrYesterday(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsYesterday();
    }
    return null;
  }
}
