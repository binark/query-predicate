package com.binark.querypredicate.management;

import com.binark.querypredicate.annotation.FilterClass;
import com.binark.querypredicate.builder.PredicateBuilder;

/**
 * The predicate builder registry.
 * Help to add or replace a predicate builder in the storage
 * @see PredicateBuilderStorage
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class PredicateBuilderRegistry {

    private final PredicateBuilderStorage storage;

    public PredicateBuilderRegistry() {
        storage = BasePredicateBuilderStorage.getInstance();
    }

    public PredicateBuilderRegistry(PredicateBuilderStorage storage) {
        this.storage = storage;
    }

    /**
     * Register a new predicate builder by using the name as a key
     *
     * @param name The predicate builder custom name as a key
     * @param predicateBuilder The predicate builder to register
     */
    private void registerPredicateBuilder(String name, PredicateBuilder predicateBuilder) {
        storage.addPredicateBuilder(name, predicateBuilder);
    }

    /**
     * Register a predicate builder by using the filter class annotation value as name
     * @see FilterClass
     * @param predicateBuilder The predicate builder to register
     */
    public void registerPredicateBuilder(PredicateBuilder predicateBuilder) {
        FilterClass filterClass = predicateBuilder.getClass().getAnnotation(FilterClass.class);
        registerPredicateBuilder(filterClass.value().getSimpleName(), predicateBuilder);
    }

    /**
     * Replace a built-in predicate builder
     *
     * @param filterClass The built-in filter class
     * @param predicateBuilder The new predicate builder to use
     */
    public void replacePredicateBuilder(Class filterClass, PredicateBuilder predicateBuilder) {
        registerPredicateBuilder(filterClass.getSimpleName(), predicateBuilder);
    }
}
