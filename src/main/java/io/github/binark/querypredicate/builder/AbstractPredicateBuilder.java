package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.lang.reflect.Field;
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
     * @return {@link List} of {@link Predicate} The query predicate list according the filter class
     */
    protected List<Predicate> buildBaseFilterPredicate(Path path, CriteriaBuilder criteriaBuilder, F filter, String fieldName) {
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

        if (filter.getIsIn() != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            filterPredicates.add(inExpressions.in(filter.getIsIn().stream().toArray()));
        }

        if (filter.getIsNotIn() != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            filterPredicates.add(inExpressions.in(filter.getIsNotIn().stream().toArray()).not());
        }

        return filterPredicates;
    }

    @Override
    public String getFieldNameFromAnnotation(Field field) {
        EntityFieldName entityFieldName = field.getAnnotation(EntityFieldName.class);
        if (entityFieldName == null) {
            throw new IllegalArgumentException("Missing " + EntityFieldName.class.getSimpleName() + " annotation on the field " + field.getName());
        }
        return entityFieldName.value();
    }
}