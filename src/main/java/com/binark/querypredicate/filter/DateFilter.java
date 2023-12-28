package com.binark.querypredicate.filter;

import java.util.Date;

/**
 * The date filter type class
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DateFilter extends ComparableFilter<Date>{

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
