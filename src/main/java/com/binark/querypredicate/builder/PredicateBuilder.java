package com.binark.querypredicate.builder;

import com.binark.querypredicate.annotation.EntityFieldName;
import com.binark.querypredicate.filter.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.lang.reflect.Field;

/**
 * The predicate builder interface. Each predicate builder class should implement this interface and override its methods
 * @param <F> The filter type. Should implements {@link Filter}
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public interface PredicateBuilder<F extends Filter> {

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

    /**
     * Retrieve the entity field name mapped with the {@link EntityFieldName} annotation
     *
     * @param field The filter field
     * @return The entity field name from {@link EntityFieldName} annotation
     */
    String getFieldNameFromAnnotation(Field field);
}