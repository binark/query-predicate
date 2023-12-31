package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

/**
 * The predicate builder storage interface
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public interface PredicateBuilderStorage {

    /**
     * Retrieve predicate builder from a key
     *
     * @param key The predicate builder key (the key used for the storage)
     * @return The {@link PredicateBuilder}
     */
    PredicateBuilder getPredicateBuilder(String key);

    /**
     * Add a new predicate builder to the storage
     *
     * @param key The predicate builder key that will be used to retrieve
     * @param predicateBuilder The predicate builder to store.
     */
    void addPredicateBuilder(String key, PredicateBuilder predicateBuilder);

    /**
     * Initialize the storage with the built-in predicate builder
     */
    void initializeStorage();
}
