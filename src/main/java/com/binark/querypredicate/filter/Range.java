package com.binark.querypredicate.filter;

/**
 * The range class model to design the between rule
 *
 * @param <T> The range type, must extends {@link Comparable}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class Range <T extends Comparable>{

  private T start;
  private T end;

  public T getStart() {
    return start;
  }

  public void setStart(T start) {
    this.start = start;
  }

  public T getEnd() {
    return end;
  }

  public void setEnd(T end) {
    this.end = end;
  }
}
