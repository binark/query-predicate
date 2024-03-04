package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.Field;
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
        Predicate predicate = null;

        Object andEquals = getAndEquals(filter);
        if (andEquals != null) {
            predicate = criteriaBuilder.equal(path.get(fieldName), andEquals);
        }

        Object orEquals = getOrEquals(filter);
        if (orEquals != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.equal(path.get(fieldName), orEquals);
            } else {
                predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(path.get(fieldName), orEquals));
            }
        }

        Object andDifferent = getAndDifferent(filter);
        if (andDifferent != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.notEqual(path.get(fieldName), andDifferent);
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(path.get(fieldName), andDifferent));
            }
        }

        Object orDifferent = getOrDifferent(filter);
        if (orDifferent != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.notEqual(path.get(fieldName), orDifferent);
            } else {
                predicate = criteriaBuilder.or(predicate, criteriaBuilder.notEqual(path.get(fieldName), orDifferent));
            }
        }

        Boolean andNull = getAndNull(filter);
        if (andNull != null) {
            Predicate temporaryPredicate = null;
            if (Boolean.TRUE.equals(filter.getNull())) {
                temporaryPredicate = criteriaBuilder.isNull(path.get(fieldName));
            } else {
                temporaryPredicate = criteriaBuilder.isNotNull(path.get(fieldName));
            }
            if (predicate == null) {
                predicate = temporaryPredicate;
            } else {
                predicate = criteriaBuilder.and(predicate, temporaryPredicate);
            }
        }

        Boolean orNull = getOrNull(filter);
        if (orNull != null) {
            Predicate temporaryPredicate = null;
            if (Boolean.TRUE.equals(filter.getNull())) {
                temporaryPredicate = criteriaBuilder.isNull(path.get(fieldName));
            } else {
                temporaryPredicate = criteriaBuilder.isNotNull(path.get(fieldName));
            }
            if (predicate == null) {
                predicate = temporaryPredicate;
            } else {
                predicate = criteriaBuilder.or(predicate, temporaryPredicate);
            }
        }

        List andIn = getAndIn(filter);
        if (andIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(filter.getIsIn().stream().toArray());
            } else {
                predicate = criteriaBuilder.and(predicate, inExpressions.in(filter.getIsIn().stream().toArray()));
            }
        }

        List orIn = getOrIn(filter);
        if (orIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(filter.getIsIn().stream().toArray());
            } else {
                predicate = criteriaBuilder.or(predicate, inExpressions.in(filter.getIsIn().stream().toArray()));
            }
        }

        List andNotIn = getAndNotIn(filter);
        if (andNotIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(filter.getIsNotIn().stream().toArray()).not();
            } else {
                predicate = criteriaBuilder.and(predicate, inExpressions.in(filter.getIsNotIn().stream().toArray()).not());
            }
        }

        List orNotIn = getOrNotIn(filter);
        if (orNotIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(filter.getIsNotIn().stream().toArray()).not();
            } else {
                predicate = criteriaBuilder.or(predicate, inExpressions.in(filter.getIsNotIn().stream().toArray()).not());
            }
        }

        return predicate;
    }

    private Object getAndEquals(BaseFilter filter) {
        Object value = filter.getIsEquals();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getIsEquals();
        }
        return value;
    }

    private Object getOrEquals(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsEquals();
        }
        return null;
    }

    private Object getAndDifferent(BaseFilter filter) {
        Object value = filter.getIsDifferent();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getIsDifferent();
        }
        return value;
    }

    private Object getOrDifferent(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsDifferent();
        }
        return null;
    }

    private Boolean getAndNull(BaseFilter filter) {
        Boolean value = filter.getNull();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getNull();
        }
        return value;
    }

    private Boolean getOrNull(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNull();
        }
        return null;
    }

    private List getAndIn(BaseFilter filter) {
        List value = filter.getIsIn();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getIsIn();
        }
        return value;
    }

    private List getOrIn(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsIn();
        }
        return null;
    }

    private List getAndNotIn(BaseFilter filter) {
        List value = filter.getIsNotIn();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getIsNotIn();
        }
        return value;
    }

    private List getOrNotIn(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsNotIn();
        }
        return null;
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