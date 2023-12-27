package com.binark.querypredicate.filter;

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

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }
}

