package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * The predicate builder for the {@link BaseFilter} type
 */
public class BaseFilterPredicateBuilder<F extends BaseFilter> extends AbstractPredicateBuilder<F> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
        return this.buildBasePredicate(path, builder, filter, fieldName);
    }
}
