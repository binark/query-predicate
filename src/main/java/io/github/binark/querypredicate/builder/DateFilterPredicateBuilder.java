package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDateFilter;
import io.github.binark.querypredicate.filter.DateFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.Calendar;
import java.util.Date;

/**
 * The predicate builder for the {@link BaseDateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DateFilterPredicateBuilder extends ComparableFilterPredicateBuilder<DateFilter> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, DateFilter filter,
      String fieldName) {
    Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
    predicate = addTodayPredicate(predicate, filter, builder, path, fieldName);
    predicate = addTomorrowPredicate(predicate, filter, builder, path, fieldName);
    predicate = addYesterdayPredicate(predicate, filter, builder, path, fieldName);
    return predicate;
  }

  private Predicate addTodayPredicate(Predicate predicate, DateFilter filter, CriteriaBuilder builder,
                                      Path path, String fieldName) {
    Boolean isToday = filter.getIsToday();
    if (isToday != null) {
      Predicate todayPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      todayPredicate = isToday ? todayPredicate : todayPredicate.not();
      predicate = combinePredicate(predicate, todayPredicate, builder);
    }
    Boolean andToday = getAndToday(filter);
    if (andToday != null) {
      Predicate andTodayPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      andTodayPredicate = andToday ? andTodayPredicate : andTodayPredicate.not();
      predicate = combinePredicate(predicate, andTodayPredicate, builder);
    }
    Boolean orToday = getOrToday(filter);
    if (orToday != null) {
      Predicate orTodayPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
      orTodayPredicate = orToday ? orTodayPredicate : orTodayPredicate.not();
      predicate = combinePredicate(predicate, orTodayPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  private Predicate addTomorrowPredicate(Predicate predicate, DateFilter filter, CriteriaBuilder builder,
                                         Path path, String fieldName) {
    Boolean isTomorrow = filter.getIsTomorrow();
    if (isTomorrow != null) {
      Date tomorrow = new Date( new Date().getTime() + (1000 * 60 * 60 * 24) );
      Predicate tomorrowPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      tomorrowPredicate = isTomorrow ? tomorrowPredicate : tomorrowPredicate.not();
      predicate = combinePredicate(predicate, tomorrowPredicate, builder);
    }
    Boolean andTomorrow = getAndTomorrow(filter);
    if (andTomorrow != null) {
      Date tomorrow = new Date( new Date().getTime() + (1000 * 60 * 60 * 24) );
      Predicate andTomorrowPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      andTomorrowPredicate = andTomorrow ? andTomorrowPredicate : andTomorrowPredicate.not();
      predicate = combinePredicate(predicate, andTomorrowPredicate, builder);
    }
    Boolean orTomorrow = getOrTomorrow(filter);
    if (orTomorrow != null) {
      Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
      Predicate orTomorrowPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
      orTomorrowPredicate = orTomorrow ? orTomorrowPredicate : orTomorrowPredicate.not();
      predicate = combinePredicate(predicate, orTomorrowPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  private Predicate addYesterdayPredicate(Predicate predicate, DateFilter filter, CriteriaBuilder builder,
                                          Path path, String fieldName) {
    Boolean isYesterday = filter.getIsYesterday();
    if (isYesterday != null) {
      Date yesterday = new Date( new Date().getTime() - (1000 * 60 * 60 * 24) );
      Predicate yesterdayPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      yesterdayPredicate = isYesterday ? yesterdayPredicate : yesterdayPredicate.not();
      predicate = combinePredicate(predicate, yesterdayPredicate, builder);
    }
    Boolean andYesterday = getAndYesterday(filter);
    if (andYesterday != null) {
      Date yesterday = new Date( new Date().getTime() - (1000 * 60 * 60 * 24) );
      Predicate andYesterdayPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      andYesterdayPredicate = andYesterday ? andYesterdayPredicate : andYesterdayPredicate.not();
      predicate = combinePredicate(predicate, andYesterdayPredicate, builder);
    }
    Boolean orYesterday = getOrYesterday(filter);
    if (orYesterday != null) {
      Date yesterday = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));
      Predicate orYesterdayPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
      orYesterdayPredicate = orYesterday ? orYesterdayPredicate : orYesterdayPredicate.not();
      predicate = combinePredicate(predicate, orYesterdayPredicate, builder, CombineOperator.OR);
    }
    return predicate;
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
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsToday();
    }
    return null;
  }

  private Boolean getOrToday(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsToday();
    }
    return null;
  }

  private Boolean getAndTomorrow(DateFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsTomorrow();
    }
    return null;
  }

  private Boolean getOrTomorrow(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsTomorrow();
    }
    return null;
  }

  private Boolean getAndYesterday(DateFilter filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsYesterday();
    }
    return null;
  }

  private Boolean getOrYesterday(DateFilter filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsYesterday();
    }
    return null;
  }
}
