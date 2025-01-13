package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseComparableFilter;
import io.github.binark.querypredicate.filter.Range;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * <p>
 * The predicate builder for the {@link BaseComparableFilter} type
 * </p>
 *
 * @param <F> The filter type, should extends {@link BaseComparableFilter}
 * @author kenany (armelknyobe@gmail.com)
 */
public abstract class BaseComparableFilterPredicateBuilder<F extends BaseComparableFilter> extends BaseFilterPredicateBuilder<F> {

    /**
     * Build a predicates list for filters that implements {@link BaseComparableFilter}
     *
     * @param path      The criteria path
     * @param builder   The criteria builder
     * @param filter    The filter, must implement {@link BaseComparableFilter}
     * @param fieldName The field name
     * @return The {@link Predicate} according the filter rules
     */
    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
        Predicate predicate = super.buildBasePredicate(path, builder, filter, fieldName);
        predicate = combinePredicate(predicate, buildGreaterThanPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildGreaterThanOrEqualsToPredicate(filter, builder, path, fieldName)
                , builder);
        predicate = combinePredicate(predicate, buildLessThanPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildLessThanOrEqualsToPredicate(filter, builder, path, fieldName),
                                     builder);
        predicate = combinePredicate(predicate, buildBetweenPredicate(filter, builder, path, fieldName), builder);
        return predicate;
    }

    /**
     * Get the between predicate from the {@link Range} value.
     * Both start and end attributes from range should be not null.
     * It will throw {@link IllegalArgumentException} if one of start or end is null
     *
     * @param path      {@link Path} The criteria path
     * @param builder   {@link CriteriaBuilder} The criteria builder
     * @param range     {@link Range} The range with start and end values
     * @param fieldName The field name
     * @return The {@link Predicate} for between rule.
     */
    protected Predicate getBetweenPredicate(Path path, CriteriaBuilder builder, Range range, String fieldName) {
        Comparable start = range.getStart();
        Comparable end = range.getEnd();
        if (start == null || end == null) {
            throw new IllegalArgumentException("The between field must have both start and end value");
        }
        return builder.between(path.get(fieldName), start, end);
    }

    protected Predicate buildGreaterThanPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Comparable isGreaterThan = filter.getIsGreaterThan();
        if (isGreaterThan != null) {
            return builder.greaterThan(path.get(fieldName), isGreaterThan);
        }
        return null;
    }

    protected Predicate buildGreaterThanOrEqualsToPredicate(F filter, CriteriaBuilder builder, Path path,
                                                            String fieldName) {
        Comparable isGreaterThanOrEqualsTo = filter.getIsGreaterThanOrEqualsTo();
        if (isGreaterThanOrEqualsTo != null) {
            return builder.greaterThanOrEqualTo(path.get(fieldName), isGreaterThanOrEqualsTo);
        }
        return null;
    }

    protected Predicate buildLessThanPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Comparable isLessThan = filter.getIsLessThan();
        if (isLessThan != null) {
            return builder.lessThan(path.get(fieldName), isLessThan);
        }
        return null;
    }

    protected Predicate buildLessThanOrEqualsToPredicate(F filter, CriteriaBuilder builder, Path path,
                                                         String fieldName) {
        Comparable isLessThanOrEqualsTo = filter.getIsLessThanOrEqualsTo();
        if (isLessThanOrEqualsTo != null) {
            return builder.lessThanOrEqualTo(path.get(fieldName), isLessThanOrEqualsTo);
        }
        return null;
    }

    protected Predicate buildBetweenPredicate(F filter, CriteriaBuilder builder, Path path, String fieldName) {
        Range isBetween = filter.getIsBetween();
        if (isBetween != null) {
            return getBetweenPredicate(path, builder, isBetween, fieldName);
        }
        return null;
    }
}
