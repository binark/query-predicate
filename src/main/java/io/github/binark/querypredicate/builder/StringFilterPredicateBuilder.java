package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * The predicate builder for the {@link BaseStringFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class StringFilterPredicateBuilder extends ComparableFilterPredicateBuilder<StringFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, StringFilter filter, String fieldName) {
        Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
        predicate = addContainsPredicate(predicate, filter, builder, path, fieldName);
        predicate = addNotContainsPredicate(predicate, filter, builder, path, fieldName);
        predicate = addStartWithPredicate(predicate, filter, builder, path, fieldName);
        predicate = addEndWithPredicate(predicate, filter, builder, path, fieldName);
        predicate = addContainsIgnoreCasePredicate(predicate, filter, builder, path, fieldName);
        predicate = addNotContainsIgnoreCasePredicate(predicate, filter, builder, path, fieldName);
        predicate = addStartWithIgnoreCasePredicate(predicate, filter, builder, path, fieldName);
        predicate = addEndWithIgnoreCasePredicate(predicate, filter, builder, path, fieldName);
        return predicate;
    }

    private Predicate addContainsPredicate(Predicate predicate, StringFilter filter, CriteriaBuilder builder,
                                           Path path, String fieldName) {
        String contains = filter.getContains();
        if (contains != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), "%" + contains + "%"),
                                         builder);
        }
        String andContains = getAndContains(filter);
        if (andContains != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName),
                                                                 "%" + andContains + "%"), builder);
        }
        String orContains = getOrContains(filter);
        if (orContains != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), "%" + orContains + "%")
                    , builder, CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addNotContainsPredicate(Predicate predicate, StringFilter filter, CriteriaBuilder builder,
                                              Path path, String fieldName) {
        String notContains = filter.getNotContains();
        if (notContains != null) {
            predicate = combinePredicate(predicate, builder.notLike(path.<String>get(fieldName), "%" + notContains +
                    "%"), builder);
        }
        String andNotContains = getAndNotContains(filter);
        if (andNotContains != null) {
            predicate = combinePredicate(predicate, builder.notLike(path.<String>get(fieldName),
                                                                    "%" + andNotContains + "%"), builder);
        }
        String orNotContains = getOrNotContains(filter);
        if (orNotContains != null) {
            predicate = combinePredicate(predicate, builder.notLike(path.<String>get(fieldName),
                                                                    "%" + orNotContains + "%"), builder,
                                         CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addStartWithPredicate(Predicate predicate, StringFilter filter, CriteriaBuilder builder,
                                            Path path, String fieldName) {
        String startWith = filter.getStartWith();
        if (startWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), startWith + "%"),
                                         builder);
        }
        String andStartWith = getAndStartWith(filter);
        if (andStartWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), andStartWith + "%"),
                                         builder);
        }
        String orStartWith = getOrStartWith(filter);
        if (orStartWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), orStartWith + "%"),
                                         builder, CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addEndWithPredicate(Predicate predicate, StringFilter filter, CriteriaBuilder builder,
                                          Path path, String fieldName) {
        String endWith = filter.getEndWith();
        if (endWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), "%" + endWith), builder);
        }
        String andEndWith = getAndEndWith(filter);
        if (andEndWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), "%" + andEndWith),
                                         builder);
        }
        String orEndWith = getOrEndWith(filter);
        if (orEndWith != null) {
            predicate = combinePredicate(predicate, builder.like(path.<String>get(fieldName), "%" + orEndWith),
                                         builder, CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addContainsIgnoreCasePredicate(Predicate predicate, StringFilter filter, CriteriaBuilder builder,
                                                     Path path, String fieldName) {
        String containsIgnoreCase = filter.getContainsIgnoreCase();
        if (containsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + containsIgnoreCase.toUpperCase() + "%"),
                                         builder);
        }
        String andContainsIgnoreCase = getAndContainsIgnoreCase(filter);
        if (andContainsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + andContainsIgnoreCase.toUpperCase() + "%"),
                                         builder);
        }
        String orContainsIgnoreCase = getOrContainsIgnoreCase(filter);
        if (orContainsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + orContainsIgnoreCase.toUpperCase() + "%"),
                                         builder, CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addNotContainsIgnoreCasePredicate(Predicate predicate, StringFilter filter,
                                                        CriteriaBuilder builder,
                                                        Path path, String fieldName) {
        String notContainsIgnoreCase = filter.getNotContainsIgnoreCase();
        if (notContainsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)),
                                                                    "%" + notContainsIgnoreCase.toUpperCase() + "%"),
                                         builder);
        }
        String andNotContainsIgnoreCase = getAndNotContainsIgnoreCase(filter);
        if (andNotContainsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)),
                                                                    "%" + andNotContainsIgnoreCase.toUpperCase() +
                                                                            "%"), builder);
        }
        String orNotContainsIgnoreCase = getOrNotContainsIgnoreCase(filter);
        if (orNotContainsIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)),
                                                                    "%" + orNotContainsIgnoreCase.toUpperCase() + "%"), builder);
        }
        return predicate;
    }

    private Predicate addStartWithIgnoreCasePredicate(Predicate predicate, StringFilter filter,
                                                      CriteriaBuilder builder,
                                                      Path path, String fieldName) {
        String startWithIgnoreCase = filter.getStartWithIgnoreCase();
        if (startWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 startWithIgnoreCase.toUpperCase() + "%"), builder);
        }
        String andStartWithIgnoreCase = getAndStartWithIgnoreCase(filter);
        if (andStartWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 andStartWithIgnoreCase.toUpperCase() + "%"), builder);
        }
        String orStartWithIgnoreCase = getOrStartWithIgnoreCase(filter);
        if (orStartWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 orStartWithIgnoreCase.toUpperCase() + "%"), builder,
                                         CombineOperator.OR);
        }
        return predicate;
    }

    private Predicate addEndWithIgnoreCasePredicate(Predicate predicate, StringFilter filter,
                                                    CriteriaBuilder builder,
                                                    Path path, String fieldName) {
        String endWithIgnoreCase = filter.getEndWithIgnoreCase();
        if (endWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + endWithIgnoreCase.toUpperCase()), builder);
        }
        String andEndWithIgnoreCase = getAndEndWithIgnoreCase(filter);
        if (andEndWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + andEndWithIgnoreCase.toUpperCase()), builder);
        }
        String orEndWithIgnoreCase = getOrEndWithIgnoreCase(filter);
        if (orEndWithIgnoreCase != null) {
            predicate = combinePredicate(predicate, builder.like(builder.upper(path.<String>get(fieldName)),
                                                                 "%" + orEndWithIgnoreCase.toUpperCase()), builder,
                                         CombineOperator.OR);
        }
        return predicate;
    }

    private String getAndContains(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getContains();
        }
        return null;
    }

    private String getOrContains(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getContains();
        }
        return null;
    }

    private String getAndNotContains(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNotContains();
        }
        return null;
    }

    private String getOrNotContains(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNotContains();
        }
        return null;
    }

    private String getAndStartWith(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getStartWith();
        }
        return null;
    }

    private String getOrStartWith(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getStartWith();
        }
        return null;
    }

    private String getAndEndWith(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getEndWith();
        }
        return null;
    }

    private String getOrEndWith(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getEndWith();
        }
        return null;
    }

    private String getAndContainsIgnoreCase(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getContainsIgnoreCase();
        }
        return null;
    }

    private String getOrContainsIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getContainsIgnoreCase();
        }
        return null;
    }

    private String getAndNotContainsIgnoreCase(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNotContainsIgnoreCase();
        }
        return null;
    }

    private String getOrNotContainsIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNotContainsIgnoreCase();
        }
        return null;
    }

    private String getAndStartWithIgnoreCase(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getNotContainsIgnoreCase();
        }
        return null;
    }

    private String getOrStartWithIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getStartWithIgnoreCase();
        }
        return null;
    }

    private String getAndEndWithIgnoreCase(StringFilter filter) {
        if (filter.getAnd() != null) {
            return filter.getAnd().getEndWithIgnoreCase();
        }
        return null;
    }

    private String getOrEndWithIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getEndWithIgnoreCase();
        }
        return null;
    }
}
