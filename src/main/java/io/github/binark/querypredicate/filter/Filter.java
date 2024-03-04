package io.github.binark.querypredicate.filter;

/**
 * <p>
 * The filter type interface. Each filter type should implement this interface.
 * </p>
 * @param <T> The filter type, should match with the entity field type
 * @param <F> The filter class type, that with be used with logical operators (AND / OR)
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public interface Filter<T, F extends Filter> {

    public F getOr();

    public void setOr(F or);

    public F getAnd();

    public void setAnd(F and);
}
