package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseTemporalFilter;
import io.github.binark.querypredicate.filter.Range;
import io.github.binark.querypredicate.filter.TemporalFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.time.temporal.Temporal;

/**
 * The predicate builder for the {@link BaseTemporalFilter} type
 *
 * @param <F> filter
 * @param <T> data type
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class TemporalFilterPredicateBuilder<F extends TemporalFilter, T extends Temporal & Comparable> extends ComparableFilterPredicateBuilder<F> {

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter,
      String fieldName) {
    Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
    predicate = addTodayPredicate(predicate, filter, builder, path, fieldName);
    predicate = addTomorrowPredicate(predicate, filter, builder, path, fieldName);
    predicate = addYesterdayPredicate(predicate, filter, builder, path, fieldName);
    return predicate;
  }

  protected Predicate addTodayPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                        Path path, String fieldName) {
    Boolean isToday = filter.getIsToday();
    if (isToday != null) {
      Predicate todayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                              getNow(), fieldName);
      todayPredicate = isToday ? todayPredicate : todayPredicate.not();
      predicate = combinePredicate(predicate, todayPredicate, builder);
    }
    Boolean andToday = getAndToday(filter);
    if (andToday != null) {
      Predicate andTodayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                 getNow(), fieldName);
      andTodayPredicate = andToday ? andTodayPredicate : andTodayPredicate.not();
      predicate = combinePredicate(predicate, andTodayPredicate, builder);
    }

    Boolean orToday = getOrToday(filter);
    if (orToday != null) {
      Predicate orTodayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                getNow(), fieldName);
      orTodayPredicate = orToday ? orTodayPredicate : orTodayPredicate.not();
      predicate = combinePredicate(predicate, orTodayPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addTomorrowPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                           Path path, String fieldName) {
    Boolean isTomorrow = filter.getIsTomorrow();
    if (isTomorrow != null) {
      Predicate tomorrowPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                 shiftTo(1), fieldName);
      tomorrowPredicate = isTomorrow ? tomorrowPredicate : tomorrowPredicate.not();
      predicate = combinePredicate(predicate, tomorrowPredicate, builder);
    }

    Boolean andTomorrow = getAndTomorrow(filter);
    if (andTomorrow != null) {
      Predicate andTomorrowPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                    shiftTo(1), fieldName);
      andTomorrowPredicate = andTomorrow ? andTomorrowPredicate : andTomorrowPredicate.not();
      predicate = combinePredicate(predicate, andTomorrowPredicate, builder);
    }

    Boolean orTomorrow = getOrTomorrow(filter);
    if (orTomorrow != null) {
      Predicate orTomorrowPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                   shiftTo(1), fieldName);
      orTomorrowPredicate = orTomorrow ? orTomorrowPredicate : orTomorrowPredicate.not();
      predicate = combinePredicate(predicate, orTomorrowPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  protected Predicate addYesterdayPredicate(Predicate predicate, F filter, CriteriaBuilder builder,
                                            Path path, String fieldName) {
    Boolean isYesterday = filter.getIsYesterday();
    if (isYesterday != null) {
      Predicate yesterdayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                  shiftFrom(1), fieldName);
      yesterdayPredicate = isYesterday ? yesterdayPredicate : yesterdayPredicate.not();
      predicate = combinePredicate(predicate, yesterdayPredicate, builder);
    }

    Boolean andYesterday = getAndYesterday(filter);
    if (andYesterday != null) {
      Predicate andYesterdayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                     shiftFrom(1), fieldName);
      andYesterdayPredicate = andYesterday ? andYesterdayPredicate : andYesterdayPredicate.not();
      predicate = combinePredicate(predicate, andYesterdayPredicate, builder);
    }

    Boolean orYesterday = getOrYesterday(filter);
    if (orYesterday != null) {
      Predicate orYesterdayPredicate = getLocalDateBetweenPredicate(path, builder,
                                                                    shiftFrom(1), fieldName);
      orYesterdayPredicate = orYesterday ? orYesterdayPredicate : orYesterdayPredicate.not();
      predicate = combinePredicate(predicate, orYesterdayPredicate, builder, CombineOperator.OR);
    }
    return predicate;
  }

  private Predicate getLocalDateBetweenPredicate(Path path, CriteriaBuilder builder, T localDate, String fieldName) {
    Range<T> range = new Range<>();
    range.setStart(atStartOfDay(localDate));
    range.setEnd(atEndOfDay(localDate));
    return getBetweenPredicate(path, builder, range, fieldName);
  }

  protected abstract T getNow();


  /**
   * Date + value in day
   * @param value
   * @return
   */
  protected abstract T shiftTo(int value);


  /**
   * Date minus value in day
   * @param value
   * @return
   */
  protected abstract T shiftFrom(int value);

  protected abstract T atStartOfDay(T date);

  protected abstract T atEndOfDay(T date);

  private Boolean getAndToday(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsToday();
    }
    return null;
  }

  private Boolean getOrToday(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsToday();
    }
    return null;
  }

  private Boolean getAndTomorrow(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsTomorrow();
    }
    return null;
  }

  private Boolean getOrTomorrow(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsTomorrow();
    }
    return null;
  }

  private Boolean getAndYesterday(F filter) {
    if (filter.getAnd() != null) {
      return filter.getAnd().getIsYesterday();
    }
    return null;
  }

  private Boolean getOrYesterday(F filter) {
    if (filter.getOr() != null) {
      return filter.getOr().getIsYesterday();
    }
    return null;
  }
}
