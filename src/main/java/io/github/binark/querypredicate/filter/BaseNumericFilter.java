package io.github.binark.querypredicate.filter;

/**
 * The numeric filter type class. extends {@link BaseComparableFilter}
 *
 * @param <T> The numeric type, must extends {@link Number} and {@link Comparable}
 * @author kenany (armelknyobe@gmail.com)
 * @see BaseComparableFilter
 */
public abstract class BaseNumericFilter<T extends Number & Comparable> extends BaseComparableFilter<T> {

}
