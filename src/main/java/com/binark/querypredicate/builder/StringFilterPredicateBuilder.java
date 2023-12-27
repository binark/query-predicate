package com.binark.querypredicate.builder;


import com.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * The predicate builder for the {@link StringFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class StringFilterPredicateBuilder extends AbstractPredicateBuilder<StringFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, StringFilter filter, String fieldName) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = buildBaseFilterPredicate(path, builder, filter, fieldName);
        predicates.add(predicate);

        if (filter.getContains() != null) {
            predicates.add(builder.like(path.<String>get(fieldName), "%" + filter.getContains() + "%"));
        }

        if (filter.getNoContains() != null) {
            predicates.add(builder.notLike(
                path.<String>get(fieldName), "%" + filter.getNoContains() + "%"));
        }

        if (filter.getStartWith() != null) {
            predicates.add(builder.like(path.<String>get(fieldName), filter.getStartWith() + "%"));
        }

        if (filter.getEndWith() != null) {
            predicates.add(builder.like(path.<String>get(fieldName), "%" + filter.getEndWith()));
        }

        if (filter.getContainsIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(path.<String>get(fieldName)), "%" + filter.getContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getNoContainsIgnoreCase() != null) {
            predicates.add(builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + filter.getNoContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getStartWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(path.<String>get(fieldName)),  filter.getNoContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getEndWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(path.<String>get(fieldName)), "%" + filter.getEndWithIgnoreCase().toUpperCase()));
        }

        return builder.or(predicates.toArray(new Predicate[0]));
    }
}
