package io.github.binark.querypredicate.filter;

/**
 * The boolean filter type class
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BooleanFilter extends ComparableFilter<Boolean>{

  private Boolean isTrue;
  private Boolean isFalse;

  /**
   * The getter of is true
   * @return a boolean
   */
  public Boolean getTrue() {
    return isTrue;
  }

  /**
   * The setter of is true
   * @param  isTrue boolean
   */
  public void setTrue(Boolean isTrue) {
    isTrue = isTrue;
  }

  /**
   * The getter of is false
   * @return a boolean
   */
  public Boolean getFalse() {
    return isFalse;
  }

  /**
   * The setter of is false
   * @param  isFalse boolean
   */
  public void setFalse(Boolean isFalse) {
    isFalse = isFalse;
  }
}
