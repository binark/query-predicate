package io.github.binark.querypredicate.management;

import io.github.binark.querypredicate.annotation.FilterClass;
import io.github.binark.querypredicate.builder.*;
import io.github.binark.querypredicate.filter.*;

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

    private static BasePredicateBuilderStorage instance;

    private BasePredicateBuilderStorage() {
        initializeStorage();
    }

    static BasePredicateBuilderStorage getInstance() {
        if (instance == null) {
            instance = new BasePredicateBuilderStorage();
        }
        return instance;
    }

    public PredicateBuilder getPredicateBuilder(String key) {
        return predicateBuilderMap.get(key);
    }

    public void addPredicateBuilder(String key, PredicateBuilder predicateBuilder) {
        predicateBuilderMap.put(key, predicateBuilder);
    }

    public void initializeStorage() {
        predicateBuilderMap.put(BaseFilter.class.getSimpleName(), new BaseFilterPredicateBuilder());
        predicateBuilderMap.put(OperatorFilter.class.getSimpleName(), new OperatorFilterPredicateBuilder());
        predicateBuilderMap.put(BaseStringFilter.class.getSimpleName(), new BaseStringFilterPredicateBuilder());
        predicateBuilderMap.put(StringFilter.class.getSimpleName(), new StringFilterPredicateBuilder());
        predicateBuilderMap.put(
                BaseBooleanFilter.class.getSimpleName(), new BaseBooleanFilterPredicateBuilder());
        predicateBuilderMap.put(
                BooleanFilter.class.getSimpleName(), new BooleanFilterPredicateBuilder());
        predicateBuilderMap.put(BaseDateFilter.class.getSimpleName(), new BaseDateFilterPredicateBuilder());
        predicateBuilderMap.put(DateFilter.class.getSimpleName(), new DateFilterPredicateBuilder());
        predicateBuilderMap.put(
                BaseLocalDateFilter.class.getSimpleName(), new BaseLocalDateFilterPredicateBuilder());
        predicateBuilderMap.put(
                LocalDateFilter.class.getSimpleName(), new LocalDateFilterPredicateBuilder());
        predicateBuilderMap.put(
                BaseLocalDateTimeFilter.class.getSimpleName(), new BaseLocalDateTimeFilterPredicateBuilder());
        predicateBuilderMap.put(
                LocalDateTimeFilter.class.getSimpleName(), new LocalDateTimeFilterPredicateBuilder());
        predicateBuilderMap.put(
                BaseInstantFilter.class.getSimpleName(), new BaseInstantFilterPredicateBuilder());
        predicateBuilderMap.put(
                InstantFilter.class.getSimpleName(), new InstantFilterPredicateBuilder());
        predicateBuilderMap.put(
                BaseIntegerFilter.class.getSimpleName(), new BaseIntegerFilterPredicateBuilder());
        predicateBuilderMap.put(
                IntegerFilter.class.getSimpleName(), new IntegerFilterPredicateBuilder());
        predicateBuilderMap.put(BaseLongFilter.class.getSimpleName(), new BaseLongFilterPredicateBuilder());
        predicateBuilderMap.put(LongFilter.class.getSimpleName(), new LongFilterPredicateBuilder());
        predicateBuilderMap.put(BaseFloatFilter.class.getSimpleName(), new BaseFloatFilterPredicateBuilder());
        predicateBuilderMap.put(FloatFilter.class.getSimpleName(), new FloatFilterPredicateBuilder());
        predicateBuilderMap.put(BaseDoubleFilter.class.getSimpleName(), new BaseDoubleFilterPredicateBuilder());
        predicateBuilderMap.put(DoubleFilter.class.getSimpleName(), new DoubleFilterPredicateBuilder());
    }
}
