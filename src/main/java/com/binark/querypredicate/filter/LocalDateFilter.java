package com.binark.querypredicate.filter;

import java.time.temporal.Temporal;

/**
 * The local date filter type class
 * @param <T> must implements both {@link Temporal} and {@link Comparable}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class LocalDateFilter<T extends Temporal & Comparable> extends ComparableFilter<T>{

  private Boolean isToday;
  private Boolean isTomorrow;
  private Boolean isYesterday;

  public Boolean getIsToday() {
    return isToday;
  }

  public void setIsToday(Boolean isToday) {
    this.isToday = isToday;
  }

  public Boolean getIsTomorrow() {
    return isTomorrow;
  }

  public void setIsTomorrow(Boolean isTomorrow) {
    this.isTomorrow = isTomorrow;
  }

  public Boolean getIsYesterday() {
    return isYesterday;
  }

  public void setIsYesterday(Boolean isYesterday) {
    this.isYesterday = isYesterday;
  }
}
