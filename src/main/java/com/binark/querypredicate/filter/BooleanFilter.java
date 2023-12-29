package com.binark.querypredicate.filter;

/**
 * The boolean filter type class
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BooleanFilter extends ComparableFilter<Boolean>{

  private Boolean isTrue;
  private Boolean isFalse;

  public Boolean getTrue() {
    return isTrue;
  }

  public void setTrue(Boolean aTrue) {
    isTrue = aTrue;
  }

  public Boolean getFalse() {
    return isFalse;
  }

  public void setFalse(Boolean aFalse) {
    isFalse = aFalse;
  }
}
