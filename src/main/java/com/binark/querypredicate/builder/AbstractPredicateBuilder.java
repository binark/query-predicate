package com.binark.querypredicate.builder;

import com.binark.querypredicate.annotation.EntityFieldName;
import com.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    protected Predicate buildBaseFilterPredicate(Path path, CriteriaBuilder criteriaBuilder, F filter, String fieldName) {
        List<Predicate> filterPredicates = new ArrayList<>();
        if (filter.getIsEquals() != null) {
            filterPredicates.add(criteriaBuilder.equal(path.get(fieldName), filter.getIsEquals()));
        }

        if (filter.getIsDifferent() != null) {
            filterPredicates.add(criteriaBuilder.notEqual(path.get(fieldName), filter.getIsDifferent()));
        }

        if (Boolean.TRUE.equals(filter.getNull())) {
            filterPredicates.add(criteriaBuilder.isNull(path.get(fieldName)));
        }

        if (Boolean.FALSE.equals(filter.getNull())) {
            filterPredicates.add(criteriaBuilder.isNotNull(path.get(fieldName)));
        }

        return criteriaBuilder.or(filterPredicates.toArray(new Predicate[0]));
    }

    /**
     * Retrieve the entity field name mapped with the {@link EntityFieldName} annotation
     *
     * @param filter The filter. Should extends {@link BaseFilter}
     * @return The entity field name from {@link EntityFieldName} annotation
     */
    protected String getFieldNameFromAnnotation(F filter) {
        EntityFieldName entityFieldName = filter.getClass().getAnnotation(EntityFieldName.class);
        return entityFieldName.value();
    }
}