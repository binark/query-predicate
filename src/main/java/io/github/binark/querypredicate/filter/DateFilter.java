package io.github.binark.querypredicate.filter;

import java.util.Date;

/**
 * <p>The date filter type class</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class DateFilter extends ComparableFilter<Date>{

  private Boolean isToday;
  private Boolean isTomorrow;
  private Boolean isYesterday;

  /**
   * The getter of is today
   * @return true if today condition is true
   */
  public Boolean getIsToday() {
    return isToday;
  }

  /**
   * The setter of is today
   * @param isToday a boolean
   */
  public void setIsToday(Boolean isToday) {
    this.isToday = isToday;
  }

  /**
   * The getter of is tomorrow
   * @return true if tomorrow condition is true
   */
  public Boolean getIsTomorrow() {
    return isTomorrow;
  }

  /**
   * The setter of is tomorrow
   * @param isTomorrow a boolean
   */
  public void setIsTomorrow(Boolean isTomorrow) {
    this.isTomorrow = isTomorrow;
  }

  /**
   * The getter of is yesterday
   * @return true if yesterday condition is true
   */
  public Boolean getIsYesterday() {
    return isYesterday;
  }

  /**
   * The setter of is yesterday
   * @param isYesterday a boolean
   */
  public void setIsYesterday(Boolean isYesterday) {
    this.isYesterday = isYesterday;
  }
}
