package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.filter.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
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

        Object isEquals = filter.getIsEquals();
        if (isEquals != null) {
            predicate = criteriaBuilder.equal(path.get(fieldName), isEquals);
        }

        Object andEquals = getAndEquals(filter);
        if (andEquals != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.equal(path.get(fieldName), andEquals);
            } else  {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(path.get(fieldName), andEquals));
            }
        }

        Object orEquals = getOrEquals(filter);
        if (orEquals != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.equal(path.get(fieldName), orEquals);
            } else {
                predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(path.get(fieldName), orEquals));
            }
        }

        Object isDifferent = filter.getIsDifferent();
        if (isDifferent != null) {
            if (predicate == null) {
                predicate = criteriaBuilder.notEqual(path.get(fieldName), isDifferent);
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(path.get(fieldName), isDifferent));
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

        Boolean filterNull = filter.getNull();
        if (filterNull != null) {
            Predicate temporaryPredicate;
            if (Boolean.TRUE.equals(filterNull)) {
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

        List isIn = filter.getIsIn();
        if (isIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(isIn);
            } else {
                predicate = criteriaBuilder.and(predicate, fieldPath.in(isIn));
            }
        }

        List andIn = getAndIn(filter);
        if (andIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(andIn);
            } else {
                predicate = criteriaBuilder.and(predicate, fieldPath.in(andIn));
            }
        }

        List orIn = getOrIn(filter);
        if (orIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(orIn);
            } else {
                predicate = criteriaBuilder.or(predicate, fieldPath.in(orIn));
            }
        }

        List isNotIn = filter.getIsNotIn();
        if (isNotIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(isNotIn).not();
            } else {
                predicate = criteriaBuilder.and(predicate, fieldPath.in(isNotIn).not());
            }
        }

        List andNotIn = getAndNotIn(filter);
        if (andNotIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(andNotIn).not();
            } else {
                predicate = criteriaBuilder.and(predicate, fieldPath.in(andNotIn).not());
            }
        }

        List orNotIn = getOrNotIn(filter);
        if (orNotIn != null) {
            Path<Object> fieldPath = path.get(fieldName);
            if (predicate == null) {
                predicate = fieldPath.in(orNotIn).not();
            } else {
                predicate = criteriaBuilder.or(predicate, fieldPath.in(orNotIn).not());
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
        if (filter.getOr() != null) {
            return filter.getOr().getIsEquals();
        }
        return null;
    }

    private Object getAndDifferent(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsDifferent();
        }
        return null;
    }

    private Object getOrDifferent(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsDifferent();
        }
        return null;
    }

    private Boolean getAndNull(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNull();
        }
        return null;
    }

    private Boolean getOrNull(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNull();
        }
        return null;
    }

    private List getAndIn(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsIn();
        }
        return null;
    }

    private List getOrIn(BaseFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsIn();
        }
        return null;
    }

    private List getAndNotIn(BaseFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsNotIn();
        }
        return null;
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