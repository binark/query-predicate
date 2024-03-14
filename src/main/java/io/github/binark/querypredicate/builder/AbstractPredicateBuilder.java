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
    protected Predicate buildBaseFilterPredicate(Path<?> path, CriteriaBuilder criteriaBuilder, F filter, String fieldName) {
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
            Predicate temporaryPredicate;
            if (Boolean.TRUE.equals(andNull)) {
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
            Predicate temporaryPredicate;
            if (Boolean.TRUE.equals(orNull)) {
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
                predicate = inExpressions.in(andIn.stream().toArray());
            } else {
                predicate = criteriaBuilder.and(predicate, inExpressions.in(andIn.stream().toArray()));
            }
        }

        List orIn = getOrIn(filter);
        if (orIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(orIn.stream().toArray());
            } else {
                predicate = criteriaBuilder.or(predicate, inExpressions.in(orIn.stream().toArray()));
            }
        }

        List andNotIn = getAndNotIn(filter);
        if (andNotIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(andNotIn.stream().toArray()).not();
            } else {
                predicate = criteriaBuilder.and(predicate, inExpressions.in(andNotIn.stream().toArray()).not());
            }
        }

        List orNotIn = getOrNotIn(filter);
        if (orNotIn != null) {
            In inExpressions = criteriaBuilder.in(path.get(fieldName));
            if (predicate == null) {
                predicate = inExpressions.in(orNotIn.stream().toArray()).not();
            } else {
                predicate = criteriaBuilder.or(predicate, inExpressions.in(orNotIn.stream().toArray()).not());
            }
        }

        return predicate;
    }

    private Object getAndEquals(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsEquals();
        }
        return null;
    }

    private Object getOrEquals(BaseFilter filter) {
        Object value = filter.getIsEquals();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getIsEquals();
        }
        return value;
    }

    private Object getAndDifferent(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsDifferent();
        }
        return null;
    }

    private Object getOrDifferent(BaseFilter filter) {
        Object value = filter.getIsDifferent();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getIsDifferent();
        }
        return value;
    }

    private Boolean getAndNull(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNull();
        }
        return null;
    }

    private Boolean getOrNull(BaseFilter filter) {
        Boolean value = filter.getNull();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getNull();
        }
        return value;
    }

    private List getAndIn(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsIn();
        }
        return null;
    }

    private List getOrIn(BaseFilter filter) {
        List value = filter.getIsIn();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getIsIn();
        }
        return value;
    }

    private List getAndNotIn(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsNotIn();
        }
        return null;
    }

    private List getOrNotIn(BaseFilter filter) {
        List value = filter.getIsNotIn();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getIsNotIn();
        }
        return value;
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