package io.github.binark.querypredicate.management;

import io.github.binark.querypredicate.annotation.FilterClass;
import io.github.binark.querypredicate.builder.BaseFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.BooleanFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.DateFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.DoubleFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.FloatFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.IntegerFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.LocalDateFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.LongFilterPredicateBuilder;
import io.github.binark.querypredicate.builder.PredicateBuilder;
import io.github.binark.querypredicate.builder.StringFilterPredicateBuilder;
import io.github.binark.querypredicate.filter.BaseFilter;
import io.github.binark.querypredicate.filter.BooleanFilter;
import io.github.binark.querypredicate.filter.DateFilter;
import io.github.binark.querypredicate.filter.DoubleFilter;
import io.github.binark.querypredicate.filter.FloatFilter;
import io.github.binark.querypredicate.filter.IntegerFilter;
import io.github.binark.querypredicate.filter.LocalDateFilter;
import io.github.binark.querypredicate.filter.LongFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * The base predicate builder storage.
 *
 * Stores a predicate builder with the key that could be one of:
 * <li>The filter class name</li>
 * <>The filter class annotation value</>
 * @see FilterClass
 * @see PredicateBuilderRegistry
 *
 * Used to resolver predicate builders from a key
 * @see BasePredicateBuilderResolver
 *
 * @author kenany (armelknyobe@gmail.com)
 */
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

    public PredicateBuilder getPredicateBuilder(String key) {
        return predicateBuilderMap.get(key);
    }

    public void addPredicateBuilder(String key, PredicateBuilder predicateBuilder) {
        predicateBuilderMap.put(key, predicateBuilder);
    }

    public void initializeStorage() {
        predicateBuilderMap.put(BaseFilter.class.getSimpleName(), new BaseFilterPredicateBuilder());
        predicateBuilderMap.put(StringFilter.class.getSimpleName(), new StringFilterPredicateBuilder());
        predicateBuilderMap.put(
            BooleanFilter.class.getSimpleName(), new BooleanFilterPredicateBuilder());
        predicateBuilderMap.put(DateFilter.class.getSimpleName(), new DateFilterPredicateBuilder());
        predicateBuilderMap.put(
            LocalDateFilter.class.getSimpleName(), new LocalDateFilterPredicateBuilder());
        predicateBuilderMap.put(
            IntegerFilter.class.getSimpleName(), new IntegerFilterPredicateBuilder());
        predicateBuilderMap.put(LongFilter.class.getSimpleName(), new LongFilterPredicateBuilder());
        predicateBuilderMap.put(FloatFilter.class.getSimpleName(), new FloatFilterPredicateBuilder());
        predicateBuilderMap.put(
            DoubleFilter.class.getSimpleName(), new DoubleFilterPredicateBuilder());
    }
}
