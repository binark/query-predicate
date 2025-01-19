package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author kenany (armelknyobe@gmail.com)
 *
 * Abstract predicate builder for the base filter type
 * @param <F> {@link BaseFilter}
 */
public abstract class AbstractPredicateBuilder<F extends BaseFilter> implements PredicateBuilder<F> {

    /**
     * Build the predicate for the base filter type
     *
     * @param path {@link Path} The criteria path
     * @param criteriaBuilder {@link CriteriaBuilder} The criteria builder
     * @param filter extends {@link BaseFilter} The filter type
     * @param fieldName The entity field name
     * @return {@link Predicate} The query predicate according the filter class
     */
    protected Predicate buildBasePredicate(Path<?> path, CriteriaBuilder criteriaBuilder, F filter, String fieldName) {
        Predicate predicate = combinePredicate(null, buildEqualsPredicate(filter, criteriaBuilder, path, fieldName),
                                               criteriaBuilder);
        predicate = combinePredicate(predicate, buildDifferentPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildIsNullPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildIsInPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildIsNotInPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        return predicate;
    }

    protected final Predicate combinePredicate(Predicate firstPredicate, Predicate secondPredicate,
                                               CriteriaBuilder criteriaBuilder, CombineOperator operator) {
        if (firstPredicate == null) {
            return secondPredicate;
        }
        if (secondPredicate == null) {
            return firstPredicate;
        }
        if (CombineOperator.OR.equals(operator)) {
            return criteriaBuilder.or(firstPredicate, secondPredicate);
        }
        return criteriaBuilder.and(firstPredicate, secondPredicate);
    }

    protected final Predicate combinePredicate(Predicate firstPredicate, Predicate secondPredicate,
                                               CriteriaBuilder criteriaBuilder) {
        return combinePredicate(firstPredicate, secondPredicate, criteriaBuilder, CombineOperator.AND);
    }

    protected Predicate buildEqualsPredicate(F filter, CriteriaBuilder criteriaBuilder,
                                             Path path, String fieldName) {
        Object isEquals = filter.getIsEquals();
        if (isEquals != null) {
            return criteriaBuilder.equal(path.get(fieldName), isEquals);
        }
        return null;
    }

    protected Predicate buildDifferentPredicate(F filter, CriteriaBuilder criteriaBuilder,
                                                Path path, String fieldName) {
        Object isDifferent = filter.getIsDifferent();
        if (isDifferent != null) {
            return criteriaBuilder.notEqual(path.get(fieldName), isDifferent);
        }
        return null;
    }

    protected Predicate buildIsNullPredicate(F filter, CriteriaBuilder criteriaBuilder,
                                             Path path, String fieldName) {
        Boolean filterNull = filter.getNull();
        if (filterNull != null) {
            if (Boolean.TRUE.equals(filterNull)) {
                return criteriaBuilder.isNull(path.get(fieldName));
            } else {
                return criteriaBuilder.isNotNull(path.get(fieldName));
            }
        }
        return null;
    }

    protected Predicate buildIsInPredicate(F filter, CriteriaBuilder criteriaBuilder,
                                           Path path, String fieldName) {
        List isIn = filter.getIsIn();
        if (isIn != null) {
            return path.get(fieldName).in(isIn);
        }
        return null;
    }

    protected Predicate buildIsNotInPredicate(F filter, CriteriaBuilder criteriaBuilder,
                                              Path path, String fieldName) {
        List isNotIn = filter.getIsNotIn();
        if (isNotIn != null) {
            return path.get(fieldName).in(isNotIn).not();
        }
        return null;
    }

    @Override
    public String getFieldNameFromAnnotation(Field field) {
        EntityFieldName entityFieldName = field.getAnnotation(EntityFieldName.class);
        if (entityFieldName == null) {
            throw new IllegalArgumentException("Missing " + EntityFieldName.class.getSimpleName() + " annotation on the field " + field.getName());
        }
        return entityFieldName.value();
    }

    protected enum CombineOperator {
        AND, OR;
    }
}