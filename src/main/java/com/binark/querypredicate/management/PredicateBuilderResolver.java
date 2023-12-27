package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

/**
 * The predicate builder resolver.
 * Uses the storage to resolve predicate builders
 * @see PredicateBuilderStorage
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class PredicateBuilderResolver {

    /**
     * Resolve a predicate builder from Its name
     *
     * @param name The predicate builder name (the key)
     * @return The predicate builder
     */
    private PredicateBuilder resolverPredicateBuilder(String name) {
        return BasePredicateBuilderStorage.getInstance().getPredicateBuilder(name);
    }

    /**
     * resolve a predicate builder from Its filter class.
     * Most used for the built-in filter
     *
     * @param filterClass The filter class
     * @return The predicate builder
     */
    public PredicateBuilder resolverPredicateBuilder(Class filterClass) {
        return resolverPredicateBuilder(filterClass.getSimpleName());
    }
}