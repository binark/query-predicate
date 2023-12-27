package com.binark.querypredicate.filter;

public class StringFilter extends BaseFilter<String>{

    private String contains;
    private String noContains;
    private String startWith;
    private String endWith;
    private String containsIgnoreCase;
    private String noContainsIgnoreCase;
    private String startWithIgnoreCase;
    private String endWithIgnoreCase;

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getNoContains() {
        return noContains;
    }

    public void setNoContains(String noContains) {
        this.noContains = noContains;
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

    public String getNoContainsIgnoreCase() {
        return noContainsIgnoreCase;
    }

    public void setNoContainsIgnoreCase(String noContainsIgnoreCase) {
        this.noContainsIgnoreCase = noContainsIgnoreCase;
    }
}