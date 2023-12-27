package com.binark.querypredicate.builder;

import com.binark.querypredicate.annotation.EntityFieldName;
import com.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPredicateBuilder<F extends BaseFilter> implements PredicateBuilder<F> {

    protected Predicate buildBaseFilterPredicate(Path root, CriteriaBuilder criteriaBuilder, F filter, String fieldName) {
        List<Predicate> filterPredicates = new ArrayList<>();
        if (filter.getIsEquals() != null) {
            filterPredicates.add(criteriaBuilder.equal(root.get(fieldName), filter.getIsEquals()));
        }

        if (filter.getIsDifferent() != null) {
            filterPredicates.add(criteriaBuilder.notEqual(root.get(fieldName), filter.getIsDifferent()));
        }

        if (Boolean.TRUE.equals(filter.getNull())) {
            filterPredicates.add(criteriaBuilder.isNull(root.get(fieldName)));
        }

        if (Boolean.FALSE.equals(filter.getNull())) {
            filterPredicates.add(criteriaBuilder.isNotNull(root.get(fieldName)));
        }

        return criteriaBuilder.or(filterPredicates.toArray(new Predicate[0]));
    }

    protected String getFieldNameFromAnnotation(F filter) {
        EntityFieldName entityFieldName = filter.getClass().getAnnotation(EntityFieldName.class);
        return entityFieldName.value();
    }
}