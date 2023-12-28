package com.binark.querypredicate.filter;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The boolean filter type class
 */
public class BooleanFilter extends BaseFilter<Boolean>{

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