package com.binark.querypredicate.builder;


import com.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class BaseFilterPredicateBuilder extends AbstractPredicateBuilder<BaseFilter>{
    @Override
    public Predicate buildPredicate(Path root, CriteriaBuilder builder, BaseFilter filter) {
        return buildBaseFilterPredicate(root, builder, filter, getFieldNameFromAnnotation(filter));
    }

    @Override
    public Predicate buildPredicate(Path root, CriteriaBuilder builder, BaseFilter filter, String fieldName) {
        return buildBaseFilterPredicate(root, builder, filter, fieldName);
    }
}
