package io.github.binark.querypredicate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * Map a filter class to a predicate builder
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterClass {

    /**
     * The filter class
     * @return The filter class
     */
    public Class value();
}
