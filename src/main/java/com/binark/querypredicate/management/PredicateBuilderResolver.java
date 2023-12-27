package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.PredicateBuilder;

public class PredicateBuilderResolver {

    private PredicateBuilder resolverPredicateBuilder(String name) {
        return BasePredicateBuilderStorage.getInstance().getPredicateBuilder(name);
    }

    public PredicateBuilder resolverPredicateBuilder(Class filterClass) {
        return resolverPredicateBuilder(filterClass.getSimpleName());
    }
}