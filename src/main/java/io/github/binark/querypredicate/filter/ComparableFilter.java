package io.github.binark.querypredicate.filter;

/**
 * The comparable filter type class
 * @param <T> The comparable type, must extends {@link Comparable}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class ComparableFilter<T extends Comparable, F extends ComparableFilter> extends BaseFilter<T, F> {

  private T isGreaterThan;

  private T isGreaterThanOrEqualsTo;

  private T isLessThan;

  private T isLessThanOrEqualsTo;

  private Range<T> isBetween;

  /**
   * The getter of is greater than
   * @return object of type T
   */
  public T getIsGreaterThan() {
    return isGreaterThan;
  }

  /**
   * The setter of is greater than
   * @param isGreaterThan object of type T
   */
  public void setIsGreaterThan(T isGreaterThan) {
    this.isGreaterThan = isGreaterThan;
  }

  /**
   * The getter of is greater than or equals to
   * @return object of type T
   */
  public T getIsGreaterThanOrEqualsTo() {
    return isGreaterThanOrEqualsTo;
  }

  /**
   * The setter of is greater than or equals to
   * @param isGreaterThanOrEqualsTo object of type T
   */
  public void setIsGreaterThanOrEqualsTo(T isGreaterThanOrEqualsTo) {
    this.isGreaterThanOrEqualsTo = isGreaterThanOrEqualsTo;
  }

  /**
   * The getter of is less than
   * @return object of type T
   */
  public T getIsLessThan() {
    return isLessThan;
  }

  /**
   * The setter of is less than
   * @param isLessThan object of type T
   */
  public void setIsLessThan(T isLessThan) {
    this.isLessThan = isLessThan;
  }

  /**
   * The getter of is less than or equals to
   * @return object of type T
   */
  public T getIsLessThanOrEqualsTo() {
    return isLessThanOrEqualsTo;
  }

  /**
   * The setter of is less than or equals to
   * @param isLessThanOrEqualsTo object of type T
   */
  public void setIsLessThanOrEqualsTo(T isLessThanOrEqualsTo) {
    this.isLessThanOrEqualsTo = isLessThanOrEqualsTo;
  }

  /**
   * The getter of is between
   * @return object of type {@link Range} of T
   * @see Range
   */
  public Range<T> getIsBetween() {
    return isBetween;
  }

  /**
   * The setter of is between
   * @param isBetween object of type {@link Range} of T
   * @see Range
   */
  public void setIsBetween(Range<T> isBetween) {
    this.isBetween = isBetween;
  }

    @Override
    public F getOr() {
        return super.getOr();
    }

    @Override
    public void setOr(F or) {
        super.setOr(or);
    }

    @Override
    public F getAnd() {
        return super.getAnd();
    }

    @Override
    public void setAnd(F and) {
        super.setAnd(and);
    }
}
