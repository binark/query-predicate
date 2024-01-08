package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 * The predicate builder for the {@link StringFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class StringFilterPredicateBuilder extends ComparableFilterPredicateBuilder<StringFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, StringFilter filter, String fieldName) {
        List<Predicate> predicates = buildComparablePredicate(path, builder, filter, fieldName);

        if (filter.getContains() != null) {
            predicates.add(builder.like(path.<String>get(fieldName), "%" + filter.getContains() + "%"));
        }

        if (filter.getNotContains() != null) {
            predicates.add(builder.notLike(
                path.<String>get(fieldName), "%" + filter.getNotContains() + "%"));
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

        if (filter.getNotContainsIgnoreCase() != null) {
            predicates.add(builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + filter.getNotContainsIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getStartWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(path.<String>get(fieldName)),  filter.getStartWithIgnoreCase().toUpperCase() + "%"));
        }

        if (filter.getEndWithIgnoreCase() != null) {
            predicates.add(builder.like(builder.upper(path.<String>get(fieldName)), "%" + filter.getEndWithIgnoreCase().toUpperCase()));
        }

        return predicates.size() == 1 ? predicates.get(0) : builder.or(predicates.toArray(new Predicate[0]));
    }
}
