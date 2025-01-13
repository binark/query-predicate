package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseNumericFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>The predicate builder for the {@link BaseNumericFilter} type</p>
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class BaseNumericFilterPredicateBuilder<F extends BaseNumericFilter> extends BaseComparableFilterPredicateBuilder<F> {

    /**
     * Build a predicates list for filters that extends {@link BaseNumericFilter}.
     * Just like {@link BaseComparableFilterPredicateBuilder}, but with the numeric method from {@link CriteriaBuilder}
     *
     * @param path      The criteria path
     * @param builder   The criteria builder
     * @param filter    The filter, must extends {@link BaseNumericFilter}
     * @param fieldName The field name
     * @return The {@link Predicate} according the filter rules
     * @see CriteriaBuilder
     */
    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
        return super.buildPredicate(path, builder, filter, fieldName);
    }

    @Override
    protected Predicate buildGreaterThanPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Comparable isGreaterThan = filter.getIsGreaterThan();
        if (isGreaterThan != null) {
            return builder.gt(path.get(fieldName), (Number) isGreaterThan);
        }
        return null;
    }

    @Override
    protected Predicate buildGreaterThanOrEqualsToPredicate(F filter, CriteriaBuilder builder, Path path,
                                                            String fieldName) {
        Comparable isGreaterThanOrEqualsTo = filter.getIsGreaterThanOrEqualsTo();
        if (isGreaterThanOrEqualsTo != null) {
            return builder.ge(path.get(fieldName), (Number) isGreaterThanOrEqualsTo);
        }
        return null;
    }

    @Override
    protected Predicate buildLessThanPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Comparable isLessThan = filter.getIsLessThan();
        if (isLessThan != null) {
            return builder.lt(path.get(fieldName), (Number) isLessThan);
        }
        return null;
    }

    @Override
    protected Predicate buildLessThanOrEqualsToPredicate(F filter, CriteriaBuilder builder, Path path,
                                                         String fieldName) {
        Comparable isLessThanOrEqualsTo = filter.getIsLessThanOrEqualsTo();
        if (isLessThanOrEqualsTo != null) {
            return builder.le(path.get(fieldName), (Number) isLessThanOrEqualsTo);
        }
        return null;
    }

    @Override
    protected Predicate buildBetweenPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Range isBetween = filter.getIsBetween();
        if (isBetween != null) {
            return getBetweenPredicate(path, builder, isBetween, fieldName);
        }
        return null;
    }
}
