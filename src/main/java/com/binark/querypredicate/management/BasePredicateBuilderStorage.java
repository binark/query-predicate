package com.binark.querypredicate.management;

import com.binark.querypredicate.builder.BaseFilterPredicateBuilder;
import com.binark.querypredicate.builder.PredicateBuilder;
import com.binark.querypredicate.builder.StringFilterPredicateBuilder;
import com.binark.querypredicate.filter.BaseFilter;
import com.binark.querypredicate.filter.StringFilter;

import java.util.HashMap;
import java.util.Map;

final class BasePredicateBuilderStorage implements PredicateBuilderStorage {

    private final Map<String, PredicateBuilder> predicateBuilderMap = new HashMap<>();

    private static BasePredicateBuilderStorage INSTANCE;

    private BasePredicateBuilderStorage() {
        initializeStorage();
    }

    static BasePredicateBuilderStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BasePredicateBuilderStorage();
        }
        return INSTANCE;
    }

    public PredicateBuilder getPredicateBuilder(String name) {
        return predicateBuilderMap.get(name);
    }

    public void addPredicateBuilder(String name, PredicateBuilder predicateBuilder) {
        predicateBuilderMap.put(name, predicateBuilder);
    }

    private void initializeStorage() {
        predicateBuilderMap.put(BaseFilter.class.getSimpleName(), new BaseFilterPredicateBuilder());
        predicateBuilderMap.put(StringFilter.class.getSimpleName(), new StringFilterPredicateBuilder());
    }
}
