package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.Range;
import io.github.binark.querypredicate.filter.TemporalFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.time.temporal.Temporal;

/**
 * The predicate builder for the {@link TemporalFilter} type
 *
 * @param <F> filter
 * @param <T> data type
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class TemporalFilterPredicateBuilder<F extends TemporalFilter, T extends Temporal & Comparable> extends ComparableFilterPredicateBuilder<F>{

  @Override
  public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter,
      String fieldName) {
    Predicate predicate = super.buildComparablePredicate(path, builder, filter, fieldName);

    Boolean isToday = filter.getIsToday();
    if (isToday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              getNow(), fieldName);
      predicate = computeDateBetweenPredicate(isToday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean andToday = getAndToday(filter);
    if (andToday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              getNow(), fieldName);
      predicate = computeDateBetweenPredicate(andToday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orToday = getOrToday(filter);
    if (orToday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              getNow(), fieldName);
      predicate = computeDateBetweenPredicate(orToday, predicate, localDateBetweenPredicate, builder, true);
    }

    Boolean isTomorrow = filter.getIsTomorrow();
    if (isTomorrow != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftTo(1), fieldName);
      predicate = computeDateBetweenPredicate(isTomorrow, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean andTomorrow = getAndTomorrow(filter);
    if (andTomorrow != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftTo(1), fieldName);
      predicate = computeDateBetweenPredicate(andTomorrow, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orTomorrow = getOrTomorrow(filter);
    if (orTomorrow != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftTo(1), fieldName);
      predicate = computeDateBetweenPredicate(orTomorrow, predicate, localDateBetweenPredicate, builder, true);
    }

    Boolean isYesterday = filter.getIsYesterday();
    if (isYesterday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftFrom(1), fieldName);
      predicate = computeDateBetweenPredicate(isYesterday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean andYesterday = getAndYesterday(filter);
    if (andYesterday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftFrom(1), fieldName);
      predicate = computeDateBetweenPredicate(andYesterday, predicate, localDateBetweenPredicate, builder, false);
    }

    Boolean orYesterday = getOrYesterday(filter);
    if (orYesterday != null) {
      Predicate localDateBetweenPredicate = getLocalDateBetweenPredicate(path, builder,
              shiftFrom(1), fieldName);
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
