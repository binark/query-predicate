package com.binark.querypredicate.filter;

/**
 * The comparable filter type class
 * @param <T> The comparable type, must extends {@link Comparable}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class ComparableFilter<T extends Comparable> extends BaseFilter<T>{

  private T isGreaterThan;

  private T isGreaterThanOrEqualsTo;

  private T isLessThan;

  private T isLessThanOrEqualsTo;

  private Range<T> isBetween;

  public T getIsGreaterThan() {
    return isGreaterThan;
  }

  public void setIsGreaterThan(T isGreaterThan) {
    this.isGreaterThan = isGreaterThan;
  }

  public T getIsGreaterThanOrEqualsTo() {
    return isGreaterThanOrEqualsTo;
  }

  public void setIsGreaterThanOrEqualsTo(T isGreaterThanOrEqualsTo) {
    this.isGreaterThanOrEqualsTo = isGreaterThanOrEqualsTo;
  }

  public T getIsLessThan() {
    return isLessThan;
  }

  public void setIsLessThan(T isLessThan) {
    this.isLessThan = isLessThan;
  }

  public T getIsLessThanOrEqualsTo() {
    return isLessThanOrEqualsTo;
  }

  public void setIsLessThanOrEqualsTo(T isLessThanOrEqualsTo) {
    this.isLessThanOrEqualsTo = isLessThanOrEqualsTo;
  }

  public Range<T> getIsBetween() {
    return isBetween;
  }

  public void setIsBetween(Range<T> isBetween) {
    this.isBetween = isBetween;
  }
}
