package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseTemporalFilter;
import io.github.binark.querypredicate.filter.Range;
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
public abstract class BaseTemporalFilterPredicateBuilder<F extends BaseTemporalFilter,
        T extends Temporal & Comparable> extends BaseComparableFilterPredicateBuilder<F> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
        Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
        predicate = combinePredicate(predicate, buildTodayPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildTomorrowPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildYesterdayPredicate(filter, builder, path, fieldName), builder);
        return predicate;
    }

    protected Predicate buildTodayPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Boolean isToday = filter.getIsToday();
        if (isToday != null) {
            Predicate todayPredicate = getLocalDateBetweenPredicate(path, builder, getNow(), fieldName);
            return isToday ? todayPredicate : todayPredicate.not();
        }
        return null;
    }

    protected Predicate buildTomorrowPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Boolean isTomorrow = filter.getIsTomorrow();
        Predicate tomorrowPredicate = getLocalDateBetweenPredicate(path, builder, shiftTo(1), fieldName);
        if (isTomorrow != null) {
            return isTomorrow ? tomorrowPredicate : tomorrowPredicate.not();
        }
        return null;
    }

    protected Predicate buildYesterdayPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Boolean isYesterday = filter.getIsYesterday();
        Predicate yesterdayPredicate = getLocalDateBetweenPredicate(path, builder, shiftFrom(1), fieldName);
        if (isYesterday != null) {
            return isYesterday ? yesterdayPredicate : yesterdayPredicate.not();
        }
        return null;
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
     *
     * @param value
     * @return
     */
    protected abstract T shiftTo(int value);

    /**
     * Date minus value in day
     *
     * @param value
     * @return
     */
    protected abstract T shiftFrom(int value);

    protected abstract T atStartOfDay(T date);

    protected abstract T atEndOfDay(T date);
}
