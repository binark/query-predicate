package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseIntegerFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link BaseIntegerFilter} type. Extends all features from
 * {@link BaseNumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BaseIntegerFilterPredicateBuilder extends BaseNumericFilterPredicateBuilder<BaseIntegerFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, BaseIntegerFilter filter,
                                    String fieldName) {
        return super.buildPredicate(path, builder, filter, fieldName);
    }
}
