package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

/**
 * The predicate builder resolver.
 * Uses the storage to resolve predicate builders
 * @see PredicateBuilderStorage
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public final class BasePredicateBuilderResolver implements PredicateBuilderResolver{

    /**
     * Resolve a predicate builder from Its name
     *
     * @param name The predicate builder name (the key)
     * @return The predicate builder
     */
    private PredicateBuilder resolverPredicateBuilder(String name) {
        PredicateBuilder predicateBuilder = BasePredicateBuilderStorage.getInstance()
            .getPredicateBuilder(name);
        if (predicateBuilder == null) {
            throw new IllegalArgumentException("There is no predicate builder registered for the filter: " + name);
        }
        return predicateBuilder;
    }

    @Override
    public PredicateBuilder resolverPredicateBuilder(Class filterClass) {
        return resolverPredicateBuilder(filterClass.getSimpleName());
    }
}