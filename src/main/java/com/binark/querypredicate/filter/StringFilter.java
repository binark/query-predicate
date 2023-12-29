package com.binark.querypredicate.filter;

/**
 * The string filter type class
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class StringFilter extends ComparableFilter<String>{

    private String contains;
    private String notContains;
    private String startWith;
    private String endWith;
    private String containsIgnoreCase;
    private String notContainsIgnoreCase;
    private String startWithIgnoreCase;
    private String endWithIgnoreCase;

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getNotContains() {
        return notContains;
    }

    public void setNotContains(String notContains) {
        this.notContains = notContains;
    }

    public String getStartWith() {
        return startWith;
    }

    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }

    public String getEndWith() {
        return endWith;
    }

    public void setEndWith(String endWith) {
        this.endWith = endWith;
    }

    public String getContainsIgnoreCase() {
        return containsIgnoreCase;
    }

    public void setContainsIgnoreCase(String containsIgnoreCase) {
        this.containsIgnoreCase = containsIgnoreCase;
    }

    public String getStartWithIgnoreCase() {
        return startWithIgnoreCase;
    }

    public void setStartWithIgnoreCase(String startWithIgnoreCase) {
        this.startWithIgnoreCase = startWithIgnoreCase;
    }

    public String getEndWithIgnoreCase() {
        return endWithIgnoreCase;
    }

    public void setEndWithIgnoreCase(String endWithIgnoreCase) {
        this.endWithIgnoreCase = endWithIgnoreCase;
    }

    public String getNotContainsIgnoreCase() {
        return notContainsIgnoreCase;
    }

    public void setNotContainsIgnoreCase(String notContainsIgnoreCase) {
        this.notContainsIgnoreCase = notContainsIgnoreCase;
    }
}