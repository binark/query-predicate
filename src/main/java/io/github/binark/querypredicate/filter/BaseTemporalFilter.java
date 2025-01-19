package io.github.binark.querypredicate.filter;

import java.time.temporal.Temporal;

/**
 * The local date filter type class
 *
 * @param <T> must implements {@link Temporal}
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class BaseTemporalFilter<T extends Temporal & Comparable> extends BaseComparableFilter<T> {

    protected Boolean isToday;
    protected Boolean isTomorrow;
    protected Boolean isYesterday;

    /**
     * The getter of is today
     *
     * @return true if today condition is true
     */
    public Boolean getIsToday() {
        return isToday;
    }

    /**
     * The setter of is today
     *
     * @param isToday a boolean
     */
    public void setIsToday(Boolean isToday) {
        this.isToday = isToday;
    }

    /**
     * The getter of is tomorrow
     *
     * @return true if tomorrow condition is true
     */
    public Boolean getIsTomorrow() {
        return isTomorrow;
    }

    /**
     * The setter of is tomorrow
     *
     * @param isTomorrow a boolean
     */
    public void setIsTomorrow(Boolean isTomorrow) {
        this.isTomorrow = isTomorrow;
    }

    /**
     * The getter of is yesterday
     *
     * @return true if yesterday condition is true
     */
    public Boolean getIsYesterday() {
        return isYesterday;
    }

    /**
     * The setter of is yesterday
     *
     * @param isYesterday a boolean
     */
    public void setIsYesterday(Boolean isYesterday) {
        this.isYesterday = isYesterday;
    }
}
