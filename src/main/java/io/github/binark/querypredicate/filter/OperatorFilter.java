package io.github.binark.querypredicate.filter;

public class OperatorFilter<T, F extends BaseFilter<T>> extends BaseFilter<T> implements Filter<T> {

    private F or;

    private F and;

    public F getOr() {
        return or;
    }

    public void setOr(F or) {
        this.or = or;
    }

    public F getAnd() {
        return and;
    }

    public void setAnd(F and) {
        this.and = and;
    }
}
