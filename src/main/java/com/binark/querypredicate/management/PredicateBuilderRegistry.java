package com.binark.querypredicate.management;

import com.binark.querypredicate.annotation.FilterClass;
import com.binark.querypredicate.builder.PredicateBuilder;

public class PredicateBuilderRegistry {

    private final PredicateBuilderStorage storage;

    public PredicateBuilderRegistry() {
        storage = BasePredicateBuilderStorage.getInstance();
    }

    public PredicateBuilderRegistry(PredicateBuilderStorage storage) {
        this.storage = storage;
    }

    private void registerPredicateBuilder(String name, PredicateBuilder predicateBuilder) {
        storage.addPredicateBuilder(name, predicateBuilder);
    }

    public void registerPredicateBuilder(PredicateBuilder predicateBuilder) {
        FilterClass filterClass = predicateBuilder.getClass().getAnnotation(FilterClass.class);
        registerPredicateBuilder(filterClass.value().getSimpleName(), predicateBuilder);
    }

    public void replacePredicateBuilder(Class filterClass, PredicateBuilder predicateBuilder) {
        registerPredicateBuilder(filterClass.getSimpleName(), predicateBuilder);
    }
}
