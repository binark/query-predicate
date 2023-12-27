package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

public interface PredicateBuilderStorage {

    PredicateBuilder getPredicateBuilder(String name);

    void addPredicateBuilder(String name, PredicateBuilder predicateBuilder);
}
