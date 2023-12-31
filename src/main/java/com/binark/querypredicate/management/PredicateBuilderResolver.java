package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

/**
 * The predicate builder resolver interface.
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public interface PredicateBuilderResolver {

  /**
   * resolve a predicate builder from Its filter class.
   * Most used for the built-in filter
   *
   * @param filterClass The filter class
   * @return The predicate builder
   */
  public PredicateBuilder resolverPredicateBuilder(Class filterClass);

}
