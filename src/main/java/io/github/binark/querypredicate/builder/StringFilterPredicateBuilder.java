package io.github.binark.querypredicate.builder;


import io.github.binark.querypredicate.filter.StringFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * The predicate builder for the {@link StringFilter} type
 *
 * @author kenany (armelknyobe@gmail.com)
 */
public class StringFilterPredicateBuilder extends ComparableFilterPredicateBuilder<StringFilter> {

    @Override
    public Predicate buildPredicate(Path path, CriteriaBuilder builder, StringFilter filter, String fieldName) {
        Predicate predicate = buildComparablePredicate(path, builder, filter, fieldName);

        String contains = filter.getContains();
        if (contains != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + contains + "%");
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), "%" + contains + "%"));
            }
        }

        String andContains = getAndContains(filter);
        if (andContains != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + andContains + "%");
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), "%" + andContains + "%"));
            }
        }

        String orContains = getOrContains(filter);
        if (orContains != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + orContains + "%");
            } else {
                predicate = builder.or(predicate, builder.like(path.<String>get(fieldName), "%" + orContains + "%"));
            }
        }

        String notContains = filter.getNotContains();
        if (notContains != null) {
            if (predicate == null) {
                predicate = builder.notLike(
                        path.<String>get(fieldName), "%" + notContains + "%");
            } else {
                predicate = builder.and(predicate, builder.notLike(
                        path.<String>get(fieldName), "%" + notContains + "%"));
            }
        }

        String andNotContains = getAndNotContains(filter);
        if (andNotContains != null) {
            if (predicate == null) {
                predicate = builder.notLike(
                        path.<String>get(fieldName), "%" + andNotContains + "%");
            } else {
                predicate = builder.and(predicate, builder.notLike(
                        path.<String>get(fieldName), "%" + andNotContains + "%"));
            }
        }

        String orNotContains = getOrNotContains(filter);
        if (orNotContains != null) {
            if (predicate == null) {
                predicate = builder.notLike(
                        path.<String>get(fieldName), "%" + orNotContains + "%");
            } else {
                predicate = builder.or(predicate, builder.notLike(
                        path.<String>get(fieldName), "%" + orNotContains + "%"));
            }
        }

        String startWith = filter.getStartWith();
        if (startWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), startWith + "%");
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), startWith + "%"));
            }
        }

        String andStartWith = getAndStartWith(filter);
        if (andStartWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), andStartWith + "%");
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), andStartWith + "%"));
            }
        }

        String orStartWith = getOrStartWith(filter);
        if (orStartWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), orStartWith + "%");
            } else {
                predicate = builder.or(predicate, builder.like(path.<String>get(fieldName), orStartWith + "%"));
            }
        }

        String endWith = filter.getEndWith();
        if (endWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + endWith);
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), "%" + endWith));
            }
        }

        String andEndWith = getAndEndWith(filter);
        if (andEndWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + andEndWith);
            } else {
                predicate = builder.and(predicate, builder.like(path.<String>get(fieldName), "%" + andEndWith));
            }
        }

        String orEndWith = getOrEndWith(filter);
        if (orEndWith != null) {
            if (predicate == null) {
                predicate = builder.like(path.<String>get(fieldName), "%" + orEndWith);
            } else {
                predicate = builder.or(predicate, builder.like(path.<String>get(fieldName), "%" + orEndWith));
            }
        }

        String containsIgnoreCase = filter.getContainsIgnoreCase();
        if (containsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + containsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + containsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String andContainsIgnoreCase = getAndContainsIgnoreCase(filter);
        if (andContainsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + andContainsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + andContainsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String orContainsIgnoreCase = getOrContainsIgnoreCase(filter);
        if (orContainsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + orContainsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.or(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + orContainsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String notContainsIgnoreCase = filter.getNotContainsIgnoreCase();
        if (notContainsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + notContainsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + notContainsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String andNotContainsIgnoreCase = getAndNotContainsIgnoreCase(filter);
        if (andNotContainsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + andNotContainsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + andNotContainsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String orNotContainsIgnoreCase = getOrNotContainsIgnoreCase(filter);
        if (orNotContainsIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + orNotContainsIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.or(predicate, builder.notLike(builder.upper(path.<String>get(fieldName)), "%" + orNotContainsIgnoreCase.toUpperCase() + "%"));
            }
        }

        String startWithIgnoreCase = filter.getStartWithIgnoreCase();
        if (startWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), startWithIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), startWithIgnoreCase.toUpperCase() + "%"));
            }
        }

        String andStartWithIgnoreCase = getAndStartWithIgnoreCase(filter);
        if (andStartWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), andStartWithIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), andStartWithIgnoreCase.toUpperCase() + "%"));
            }
        }

        String orStartWithIgnoreCase = getOrStartWithIgnoreCase(filter);
        if (orStartWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), orStartWithIgnoreCase.toUpperCase() + "%");
            } else {
                predicate = builder.or(predicate, builder.like(builder.upper(path.<String>get(fieldName)), orStartWithIgnoreCase.toUpperCase() + "%"));
            }
        }

        String endWithIgnoreCase = filter.getEndWithIgnoreCase();
        if (endWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + endWithIgnoreCase.toUpperCase());
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + endWithIgnoreCase.toUpperCase()));
            }
        }

        String andEndWithIgnoreCase = getAndEndWithIgnoreCase(filter);
        if (andEndWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + andEndWithIgnoreCase.toUpperCase());
            } else {
                predicate = builder.and(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + andEndWithIgnoreCase.toUpperCase()));
            }
        }

        String orSEndWithIgnoreCase = getOrSEndWithIgnoreCase(filter);
        if (orSEndWithIgnoreCase != null) {
            if (predicate == null) {
                predicate = builder.like(builder.upper(path.<String>get(fieldName)), "%" + orSEndWithIgnoreCase.toUpperCase());
            } else {
                predicate = builder.or(predicate, builder.like(builder.upper(path.<String>get(fieldName)), "%" + orSEndWithIgnoreCase.toUpperCase()));
            }
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

    private String getOrSEndWithIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getEndWithIgnoreCase();
        }
        return null;
    }
}
