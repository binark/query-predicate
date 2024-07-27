package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.LocalDateFilter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The predicate builder for the {@link LocalDateFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LocalDateFilterPredicateBuilder extends TemporalFilterPredicateBuilder<LocalDateFilter, LocalDate> {

    @Override
    protected LocalDate getNow() {
        return LocalDate.now();
    }

    @Override
    protected LocalDate shiftTo(int value) {
        return LocalDate.now().plusDays(value);
    }

    @Override
    protected LocalDate shiftFrom(int value) {
        return LocalDate.now().minusDays(value);
    }

    @Override
    protected LocalDate atStartOfDay(LocalDate localDate) {
        return localDate.atStartOfDay().toLocalDate();
    }

    @Override
    protected LocalDate atEndOfDay(LocalDate localDate) {
        return localDate.atTime(LocalTime.MAX).toLocalDate();
    }
}
