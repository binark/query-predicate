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

    /**
     * The getter of contains rule (case-sensitive)
     * @return The contains string
     */
    public String getContains() {
        return contains;
    }

    /**
     * The setter of contains rule (case-sensitive)
     * @param contains  contains string
     */
    public void setContains(String contains) {
        this.contains = contains;
    }

    /**
     * The getter of not contains rule (case-sensitive)
     * @return The not contains string
     */
    public String getNotContains() {
        return notContains;
    }

    /**
     * The setter of  not contains rule (case-sensitive)
     * @param notContains  not contains string
     */
    public void setNotContains(String notContains) {
        this.notContains = notContains;
    }

    /**
     * The getter of start with rule (case-sensitive)
     * @return The start with string
     */
    public String getStartWith() {
        return startWith;
    }

    /**
     * The setter of start with rule (case-sensitive)
     * @param startWith start with string
     */
    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }

    /**
     * The getter of end with rule (case-sensitive)
     * @return The end with string
     */
    public String getEndWith() {
        return endWith;
    }

    /**
     * The setter of end with rule (case-sensitive)
     * @param endWith end with string
     */
    public void setEndWith(String endWith) {
        this.endWith = endWith;
    }

    /**
     * The getter of contains rule (non-case-sensitive)
     * @return The contains string
     */
    public String getContainsIgnoreCase() {
        return containsIgnoreCase;
    }

    /**
     * The setter of contains rule (non-case-sensitive)
     * @param containsIgnoreCase contains string
     */
    public void setContainsIgnoreCase(String containsIgnoreCase) {
        this.containsIgnoreCase = containsIgnoreCase;
    }

    /**
     * The getter of start with rule (non-case-sensitive)
     * @return The start with string
     */
    public String getStartWithIgnoreCase() {
        return startWithIgnoreCase;
    }

    /**
     * The setter of start with rule (non-case-sensitive)
     * @param startWithIgnoreCase start with string
     */
    public void setStartWithIgnoreCase(String startWithIgnoreCase) {
        this.startWithIgnoreCase = startWithIgnoreCase;
    }

    /**
     * The getter of end with rule (non-case-sensitive)
     * @return The end with string
     */
    public String getEndWithIgnoreCase() {
        return endWithIgnoreCase;
    }

    /**
     * The setter of end with rule (non-case-sensitive)
     * @param endWithIgnoreCase end with string
     */
    public void setEndWithIgnoreCase(String endWithIgnoreCase) {
        this.endWithIgnoreCase = endWithIgnoreCase;
    }

    /**
     * The getter of not contains rule (non-case-sensitive)
     * @return The not contains string
     */
    public String getNotContainsIgnoreCase() {
        return notContainsIgnoreCase;
    }

    /**
     * The setter of not contains rule (non-case-sensitive)
     * @param notContainsIgnoreCase not contains string
     */
    public void setNotContainsIgnoreCase(String notContainsIgnoreCase) {
        this.notContainsIgnoreCase = notContainsIgnoreCase;
    }
}