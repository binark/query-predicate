package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseDateFilter;
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
public class BaseDateFilterPredicateBuilder extends BaseComparableFilterPredicateBuilder<BaseDateFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, BaseDateFilter filter,
                                    String fieldName) {
        Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
        predicate = combinePredicate(predicate, buildTodayPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildTomorrowPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildYesterdayPredicate(filter, builder, path, fieldName), builder);
        return predicate;
    }

    private Predicate buildTodayPredicate(BaseDateFilter filter, CriteriaBuilder builder, Path path, String fieldName) {
        Boolean isToday = filter.getIsToday();
        if (isToday != null) {
            Predicate todayPredicate = getDateBetweenPredicate(path, builder, new Date(), fieldName);
            return isToday ? todayPredicate : todayPredicate.not();
        }
        return null;
    }

    private Predicate buildTomorrowPredicate(BaseDateFilter filter, CriteriaBuilder builder, Path path,
                                             String fieldName) {
        Boolean isTomorrow = filter.getIsTomorrow();
        if (isTomorrow != null) {
            Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
            Predicate tomorrowPredicate = getDateBetweenPredicate(path, builder, tomorrow, fieldName);
            return isTomorrow ? tomorrowPredicate : tomorrowPredicate.not();
        }
        return null;
    }

    private Predicate buildYesterdayPredicate(BaseDateFilter filter, CriteriaBuilder builder, Path path,
                                              String fieldName) {
        Boolean isYesterday = filter.getIsYesterday();
        if (isYesterday != null) {
            Date yesterday = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));
            Predicate yesterdayPredicate = getDateBetweenPredicate(path, builder, yesterday, fieldName);
            return isYesterday ? yesterdayPredicate : yesterdayPredicate.not();
        }
        return null;
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
