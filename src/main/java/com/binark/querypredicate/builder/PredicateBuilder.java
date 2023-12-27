package com.binark.querypredicate.builder;

import com.binark.querypredicate.filter.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * The predicate builder interface. Each predicate builder class should implement this interface and override its methods
 * @param <F> The filter type. Should implements {@link Filter}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public interface PredicateBuilder<F extends Filter> {

    /**
     * Build predicate the field that are mapped to the entity field with {@link com.binark.querypredicate.annotation.EntityFieldName}
     *
     * @param path {@link Path} The criteria path
     * @param builder {@link CriteriaBuilder} The criteria builder
     * @param filter implements {@link Filter} The filter type
     * @return {@link Predicate} The query predicate according the filter class
     */
    Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter);

    /**
     * Build predicate the field that are mapped to the entity field by the name
     *
     * @param path {@link Path} The criteria path
     * @param builder {@link CriteriaBuilder} The criteria builder
     * @param filter implements {@link Filter} The filter type
     * @param fieldName The field name
     * @return {@link Predicate} The query predicate according the filter class
     */
    Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName);
}