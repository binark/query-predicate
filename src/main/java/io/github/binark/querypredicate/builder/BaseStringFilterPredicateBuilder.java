package io.github.binark.querypredicate.builder;

import io.github.binark.querypredicate.filter.BaseStringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * The predicate builder for the {@link BaseStringFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class BaseStringFilterPredicateBuilder extends BaseComparableFilterPredicateBuilder<BaseStringFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, BaseStringFilter filter, String fieldName) {
        Predicate predicate = super.buildPredicate(path, builder, filter, fieldName);
        predicate = combinePredicate(predicate, buildContainsPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildNotContainsPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildStartWithPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildEndWithPredicate(filter, builder, path, fieldName), builder);
        predicate = combinePredicate(predicate, buildContainsIgnoreCasePredicate(filter, builder, path, fieldName),
                                     builder);
        predicate = combinePredicate(predicate, buildNotContainsIgnoreCasePredicate(filter, builder, path, fieldName)
                , builder);
        predicate = combinePredicate(predicate, buildStartWithIgnoreCasePredicate(filter, builder, path, fieldName),
                                     builder);
        predicate = combinePredicate(predicate, buildEndWithIgnoreCasePredicate(filter, builder, path, fieldName),
                                     builder);
        return predicate;
    }

    private Predicate buildContainsPredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                             String fieldName) {
        String contains = filter.getContains();
        if (contains != null) {
            return builder.like(path.<String>get(fieldName), "%" + contains + "%");
        }
        return null;
    }

    private Predicate buildNotContainsPredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                                String fieldName) {
        String notContains = filter.getNotContains();
        if (notContains != null) {
            return builder.notLike(path.<String>get(fieldName), "%" + notContains + "%");
        }
        return null;
    }

    private Predicate buildStartWithPredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                              String fieldName) {
        String startWith = filter.getStartWith();
        if (startWith != null) {
            return builder.like(path.<String>get(fieldName), startWith + "%");
        }
        return null;
    }

    private Predicate buildEndWithPredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                            String fieldName) {
        String endWith = filter.getEndWith();
        if (endWith != null) {
            return builder.like(path.<String>get(fieldName), "%" + endWith);
        }
        return null;
    }

    private Predicate buildContainsIgnoreCasePredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                                       String fieldName) {
        String containsIgnoreCase = filter.getContainsIgnoreCase();
        if (containsIgnoreCase != null) {
            return builder.like(builder.upper(path.<String>get(fieldName)), "%" + containsIgnoreCase.toUpperCase() +
                    "%");
        }
        return null;
    }

    private Predicate buildNotContainsIgnoreCasePredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                                          String fieldName) {
        String notContainsIgnoreCase = filter.getNotContainsIgnoreCase();
        if (notContainsIgnoreCase != null) {
            return builder.notLike(builder.upper(path.<String>get(fieldName)),
                                   "%" + notContainsIgnoreCase.toUpperCase() +
                    "%");
        }
        return null;
    }

    private Predicate buildStartWithIgnoreCasePredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                                        String fieldName) {
        String startWithIgnoreCase = filter.getStartWithIgnoreCase();
        if (startWithIgnoreCase != null) {
            return builder.like(builder.upper(path.<String>get(fieldName)), startWithIgnoreCase.toUpperCase() + "%");
        }
        return null;
    }

    private Predicate buildEndWithIgnoreCasePredicate(BaseStringFilter filter, CriteriaBuilder builder, Path path,
                                                      String fieldName) {
        String endWithIgnoreCase = filter.getEndWithIgnoreCase();
        if (endWithIgnoreCase != null) {
            return builder.like(builder.upper(path.<String>get(fieldName)), "%" + endWithIgnoreCase.toUpperCase());
        }
        return null;
    }
}
