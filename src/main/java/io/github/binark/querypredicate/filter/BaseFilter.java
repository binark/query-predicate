package io.github.binark.querypredicate.filter;

import java.util.List;

/**
 * <p>
 * The base filter type class
 * </p>
 * @param <T> The filter type, should match with the entity field type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BaseFilter<T> implements Filter<T>{

    private T isEquals;

    private T isDifferent;

    private Boolean isNull;

    private List<T> isIn;

    private List<T> isNotIn;

    /**
     * The getter of is equals
     * @return object of type T
     */
    public T getIsEquals() {
        return isEquals;
    }

    /**
     * The setter of is equals
     * @param isEquals object of type T
     */
    public void setIsEquals(T isEquals) {
        this.isEquals = isEquals;
    }

    /**
     * The getter of is different
     * @return object of type T
     */
    public T getIsDifferent() {
        return isDifferent;
    }

    /**
     * The setter of is different
     * @param isDifferent object of type T
     */
    public void setIsDifferent(T isDifferent) {
        this.isDifferent = isDifferent;
    }

    /**
     * The getter of is null
     * @return true if is null
     */
    public Boolean getNull() {
        return isNull;
    }

    /**
     * The setter of is null
     * @param isNull a boolean
     */
    public void setNull(Boolean isNull) {
        this.isNull = isNull;
    }

    /**
     * The getter of is in
     * @return a list of object of type T
     */
    public List<T> getIsIn() {
        return isIn;
    }

    /**
     * The setter of is in
     * @param isIn list of object of type T
     */
    public void setIsIn(List<T> isIn) {
        this.isIn = isIn;
    }

    /**
     * The getter of is not in
     * @return a list of object of type T
     */
    public List<T> getIsNotIn() {
        return isNotIn;
    }

    /**
     * The setter of is not in
     * @param isNotIn list of object of type T
     */
    public void setIsNotIn(List<T> isNotIn) {
        this.isNotIn = isNotIn;
    }
}

