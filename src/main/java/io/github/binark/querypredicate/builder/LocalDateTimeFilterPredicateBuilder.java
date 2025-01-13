package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseLocalDateTimeFilter;
import io.github.binark.querypredicate.filter.LocalDateTimeFilter;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The predicate builder for the {@link BaseLocalDateTimeFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LocalDateTimeFilterPredicateBuilder extends TemporalFilterPredicateBuilder<LocalDateTimeFilter, LocalDateTime> {

    @Override
    protected LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    @Override
    protected LocalDateTime shiftTo(int value) {
        return LocalDateTime.now().plusDays(value);
    }

    @Override
    protected LocalDateTime shiftFrom(int value) {
        return LocalDateTime.now().minusDays(value);
    }

    @Override
    protected LocalDateTime atStartOfDay(LocalDateTime localDate) {
        return localDate.toLocalDate().atStartOfDay();
    }

    @Override
    protected LocalDateTime atEndOfDay(LocalDateTime localDate) {
        return localDate.toLocalDate().atTime(LocalTime.MAX);
    }
}
