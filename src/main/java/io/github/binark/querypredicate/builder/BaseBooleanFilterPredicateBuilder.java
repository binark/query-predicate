package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseBooleanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * @author kenany (armelknyobe@gmail.com)
 * <p>
 * The predicate builder for the {@link BaseBooleanFilter} type
 */
public class BaseBooleanFilterPredicateBuilder extends BaseComparableFilterPredicateBuilder<BaseBooleanFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, BaseBooleanFilter filter, String fieldName) {
        Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
        predicate = combinePredicate(predicate, buildIsTruePredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildIsFalsePredicate(filter, builder, path, fieldName), builder);
        return predicate;
    }

    private Predicate buildIsTruePredicate(BaseBooleanFilter filter, CriteriaBuilder builder, Path path,
                                           String fieldName) {
        Boolean filterTrue = filter.getTrue();
        if (filterTrue != null) {
            return Boolean.TRUE.equals(filterTrue) ? builder.isTrue(path.get(fieldName)) :
                    builder.isFalse(path.get(fieldName));
        }
        return null;
    }

    private Predicate buildIsFalsePredicate(BaseBooleanFilter filter, CriteriaBuilder builder, Path path,
                                            String fieldName) {
        Boolean filterFalse = filter.getFalse();
        if (filterFalse != null) {
            return Boolean.TRUE.equals(filterFalse) ? builder.isFalse(path.get(fieldName)) :
                    builder.isTrue(path.get(fieldName));
        }
        return null;
    }
}
