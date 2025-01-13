package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseFilter;
import io.github.binark.querypredicate.filter.OperatorFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

/**
 * @author kenany (armelknyobe@gmail.com)
 * <p>
 * The predicate builder for the {@link BaseFilter} type
 */
public class OperatorFilterPredicateBuilder<F extends OperatorFilter> extends AbstractPredicateBuilder<F> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, F filter, String fieldName) {
        return super.buildBasePredicate(path, builder, filter, fieldName);
    }

    @Override
    protected Predicate buildEqualsPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path, String fieldName) {
        Predicate predicate = super.buildEqualsPredicate(filter, criteriaBuilder, path, fieldName);
        predicate = combinePredicate(predicate, buildAndEqualsPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildOrEqualsPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder, CombineOperator.OR);
        return predicate;
    }

    @Override
    protected Predicate buildDifferentPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                                String fieldName) {
        Predicate predicate = super.buildDifferentPredicate(filter, criteriaBuilder, path, fieldName);
        predicate = combinePredicate(predicate, buildAndDifferentPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildOrDifferentPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder, CombineOperator.OR);
        return predicate;
    }

    @Override
    protected Predicate buildIsNullPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path, String fieldName) {
        Predicate predicate = super.buildIsNullPredicate(filter, criteriaBuilder, path, fieldName);
        predicate = combinePredicate(predicate, buildAndIsNullPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildOrIsNullPredicate(filter, criteriaBuilder, path, fieldName),
                                     criteriaBuilder, CombineOperator.OR);
        return predicate;
    }

    @Override
    protected Predicate buildIsInPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path, String fieldName) {
        Predicate predicate = super.buildIsInPredicate(filter, criteriaBuilder, path, fieldName);
        predicate = combinePredicate(predicate, buildAndIsInPredicate(filter, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildOrIsInPredicate(filter, path, fieldName),
                                     criteriaBuilder, CombineOperator.OR);
        return predicate;
    }

    @Override
    protected Predicate buildIsNotInPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path, String fieldName) {
        Predicate predicate = super.buildIsNotInPredicate(filter, criteriaBuilder, path, fieldName);
        predicate = combinePredicate(predicate, buildAndIsNotInPredicate(filter, path, fieldName),
                                     criteriaBuilder);
        predicate = combinePredicate(predicate, buildOrIsNotInPredicate(filter, path, fieldName),
                                     criteriaBuilder, CombineOperator.OR);
        return predicate;
    }

    private Predicate buildAndEqualsPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                              String fieldName) {
        Object andEquals = getAndEquals(filter);
        if (andEquals != null) {
            return criteriaBuilder.equal(path.get(fieldName), andEquals);
        }
        return null;
    }

    private Predicate buildOrEqualsPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                             String fieldName) {
        Object orEquals = getOrEquals(filter);
        if (orEquals != null) {
            return criteriaBuilder.equal(path.get(fieldName), orEquals);
        }
        return null;
    }

    private Predicate buildAndDifferentPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                                 String fieldName) {
        Object andDifferent = getAndDifferent(filter);
        if (andDifferent != null) {
            return criteriaBuilder.notEqual(path.get(fieldName), andDifferent);
        }
        return null;
    }

    private Predicate buildOrDifferentPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                                String fieldName) {
        Object orDifferent = getOrDifferent(filter);
        if (orDifferent != null) {
            return criteriaBuilder.notEqual(path.get(fieldName), orDifferent);
        }
        return null;
    }

    private Predicate buildAndIsNullPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                              String fieldName) {
        Boolean andNull = getAndNull(filter);
        if (andNull != null) {
            return Boolean.TRUE.equals(andNull) ? criteriaBuilder.isNull(path.get(fieldName)) :
                    criteriaBuilder.isNotNull(path.get(fieldName));
        }
        return null;
    }

    private Predicate buildOrIsNullPredicate(F filter, CriteriaBuilder criteriaBuilder, Path path,
                                             String fieldName) {
        Boolean orNull = getOrNull(filter);
        if (orNull != null) {
            return Boolean.TRUE.equals(orNull) ? criteriaBuilder.isNull(path.get(fieldName)) :
                    criteriaBuilder.isNotNull(path.get(fieldName));
        }
        return null;
    }

    private Predicate buildAndIsInPredicate(F filter, Path path,
                                            String fieldName) {
        List andIn = getAndIn(filter);
        if (andIn != null && !andIn.isEmpty()) {
            return path.get(fieldName).in(andIn);
        }
        return null;
    }

    private Predicate buildOrIsInPredicate(F filter, Path path,
                                           String fieldName) {
        List orIn = getOrIn(filter);
        if (orIn != null && !orIn.isEmpty()) {
            return path.get(fieldName).in(orIn);
        }
        return null;
    }

    private Predicate buildAndIsNotInPredicate(F filter, Path path,
                                               String fieldName) {
        List andNotIn = getAndNotIn(filter);
        if (andNotIn != null && !andNotIn.isEmpty()) {
            return path.get(fieldName).in(andNotIn).not();
        }
        return null;
    }

    private Predicate buildOrIsNotInPredicate(F filter, Path path,
                                              String fieldName) {
        List orNotIn = getOrNotIn(filter);
        if (orNotIn != null && !orNotIn.isEmpty()) {
            return path.get(fieldName).in(orNotIn).not();
        }
        return null;
    }

    private Object getAndEquals(F filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsEquals();
        }
        return null;
    }

    private Object getOrEquals(F filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsEquals();
        }
        return null;
    }

    private Object getAndDifferent(F filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsDifferent();
        }
        return null;
    }

    private Object getOrDifferent(F filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsDifferent();
        }
        return null;
    }

    private Boolean getAndNull(F filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNull();
        }
        return null;
    }

    private Boolean getOrNull(F filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNull();
        }
        return null;
    }

    private List getAndIn(F filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsIn();
        }
        return null;
    }

    private List getOrIn(F filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsIn();
        }
        return null;
    }

    private List getAndNotIn(F filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getIsNotIn();
        }
        return null;
    }

    private List getOrNotIn(F filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getIsNotIn();
        }
        return null;
    }
}
