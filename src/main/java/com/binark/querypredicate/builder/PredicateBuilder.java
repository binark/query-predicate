package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public interface PredicateBuilder<F extends Filter> {
    Predicate buildPredicate(Path root, CriteriaBuilder builder, F filter);
    Predicate buildPredicate(Path root, CriteriaBuilder builder, F filter, String fieldName);
}