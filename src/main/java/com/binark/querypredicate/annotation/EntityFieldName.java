package com.binark.querypredicate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * Map the entity field name with the filter name
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntityFieldName {
    public String value();
}
