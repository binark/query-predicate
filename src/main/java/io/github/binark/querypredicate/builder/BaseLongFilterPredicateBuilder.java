package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseLongFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link BaseLongFilter} type. Extends all features from
 * {@link BaseNumericFilterPredicateBuilder}</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BaseLongFilterPredicateBuilder extends BaseNumericFilterPredicateBuilder<BaseLongFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, BaseLongFilter filter,
                                    String fieldName) {
        return super.buildPredicate(path, builder, filter, fieldName);
    }
}
