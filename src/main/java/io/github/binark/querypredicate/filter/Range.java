package io.github.binark.querypredicate.filter;

/**
 * <p>The range class model to design the between rule</p>
 *
 * @param <T> The range type, must extends {@link Comparable}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class Range <T extends Comparable>{

  private T start;
  private T end;

  /**
   * <p>The getter of the start item of the range.</p>
   * <p>It is always required when range is used.</p>
   * @return The object of type T
   */
  public T getStart() {
    return start;
  }

  /**
   * <p>The setter of the start item of the range.</p>
   * <p>It is always required when range is used.</p>
   * @param start The object of type T
   */
  public void setStart(T start) {
    this.start = start;
  }

  /**
   * <p>The getter of the end item of the range.</p>
   * <p>It is always required when range is used.</p>
   * @return The object of type T
   */
  public T getEnd() {
    return end;
  }

  /**
   * <p>The setter of the end item of the range.</p>
   * <p>It is always required when range is used.</p>
   * @param end The object of type T
   */
  public void setEnd(T end) {
    this.end = end;
  }
}
