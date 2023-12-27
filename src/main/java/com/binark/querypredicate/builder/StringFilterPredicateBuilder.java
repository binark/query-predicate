package com.binark.querypredicate.builder;


import com.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class StringFilterPredicateBuilder extends AbstractPredicateBuilder<StringFilter> {

    @Override
    public Predicate buildPredicate(Path root, CriteriaBuilder builder, StringFilter filter) {
        return buildPredicate(root, builder, filter, getFieldNameFromAnnotation(filter));
    }

    @Override
    public Predicate buildPredicate(Path root, CriteriaBuilder builder, StringFilter filter, String fieldName) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = buildBaseFilterPredicate(root, builder, filter, fieldName);
        predicates.add(predicate);

        if (filter.getContains() != null) {
            predicates.add(builder.like(root.<String>get(fieldName), "%" + filter.getContains() + "%"));
        }

        if (filter.getNoContains() != null) {
            predicates.add(builder.notLike(root.<String>get(fieldName), "%" + filter.getNoContains() + "%"));
        }

        if (filter.getStartWith() != null) {
            predicates.add(builder.like(root.<String>get(fieldName), filter.getStartWith() + "%"));
        }

        if (filter.getEndWith() != null) {
            predicates.add(builder.like(root.<String>get(fieldName), "%" + filter.getEndWith()));
        }

        if (filter.getContainsIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(root.<String>get(fieldName)), "%" + filter.getContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getNoContainsIgnoreCase() != null) {
            predicates.add(builder.notLike(builder.upper(root.<String>get(fieldName)), "%" + filter.getNoContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getStartWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(root.<String>get(fieldName)),  filter.getNoContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getEndWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(root.<String>get(fieldName)), "%" + filter.getEndWithIgnoreCase().toUpperCase()));
        }

        return builder.or(predicates.toArray(new Predicate[0]));
    }
}
