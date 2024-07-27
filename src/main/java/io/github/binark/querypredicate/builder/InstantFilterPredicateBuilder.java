package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.InstantFilter;

import java.time.*;
import java.time.temporal.ChronoUnit;


/**
 * The predicate builder for the {@link InstantFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class InstantFilterPredicateBuilder extends TemporalFilterPredicateBuilder<InstantFilter, Instant> {

    @Override
    protected Instant getNow() {
        return Instant.now();
    }

    @Override
    protected Instant shiftTo(int value) {
        return Instant.now().plus(value, ChronoUnit.DAYS);
    }

    @Override
    protected Instant shiftFrom(int value) {
        return Instant.now().minus(value, ChronoUnit.DAYS);
    }


    /**
     * Obtain start of a day from Instant
     * The zone id  is the default system
     * The zone offset is UTC
     *
     * @param date The date
     * @return The first second of the date as Instant
     */
    @Override
    protected Instant atStartOfDay(Instant date) {
        return LocalDate.ofInstant(date, ZoneId.systemDefault()).atStartOfDay().toInstant(ZoneOffset.UTC);
    }


    /**
     * Obtain end of a day from Instant
     * The zone id  is the default system
     * The zone offset is UTC
     *
     * @param date The date
     * @return The last second of the date as Instant
     */
    @Override
    protected Instant atEndOfDay(Instant date) {
        return LocalDate.ofInstant(date, ZoneId.systemDefault()).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
    }
}
