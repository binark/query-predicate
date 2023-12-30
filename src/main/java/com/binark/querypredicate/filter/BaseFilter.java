package com.binark.querypredicate.filter;

import java.util.List;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The base filter type class
 * @param <T>
 */
public class BaseFilter<T> implements Filter<T>{

    private T isEquals;

    private T isDifferent;

    private Boolean isNull;

    private List<T> isIn;

    private List<T> isNotIn;

    public T getIsEquals() {
        return isEquals;
    }

    public void setIsEquals(T isEquals) {
        this.isEquals = isEquals;
    }

    public T getIsDifferent() {
        return isDifferent;
    }

    public void setIsDifferent(T isDifferent) {
        this.isDifferent = isDifferent;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean isNull) {
        this.isNull = isNull;
    }

    public List<T> getIsIn() {
        return isIn;
    }

    public void setIsIn(List<T> isIn) {
        this.isIn = isIn;
    }

    public List<T> getIsNotIn() {
        return isNotIn;
    }

    public void setIsNotIn(List<T> isNotIn) {
        this.isNotIn = isNotIn;
    }
}

