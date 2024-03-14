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
        String value = filter.getContains();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getContains();
        }
        return value;
    }

    private String getOrContains(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getContains();
        }
        return null;
    }

    private String getAndNotContains(StringFilter filter) {
        String value = filter.getNotContains();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getNotContains();
        }
        return value;
    }

    private String getOrNotContains(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNotContains();
        }
        return null;
    }

    private String getAndStartWith(StringFilter filter) {
        String value = filter.getStartWith();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getStartWith();
        }
        return value;
    }

    private String getOrStartWith(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getStartWith();
        }
        return null;
    }

    private String getAndEndWith(StringFilter filter) {
        String value = filter.getEndWith();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getEndWith();
        }
        return value;
    }

    private String getOrEndWith(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getEndWith();
        }
        return null;
    }

    private String getAndContainsIgnoreCase(StringFilter filter) {
        String value = filter.getContainsIgnoreCase();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getContainsIgnoreCase();
        }
        return value;
    }

    private String getOrContainsIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getContainsIgnoreCase();
        }
        return null;
    }

    private String getAndNotContainsIgnoreCase(StringFilter filter) {
        String value = filter.getNotContainsIgnoreCase();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getNotContainsIgnoreCase();
        }
        return value;
    }

    private String getOrNotContainsIgnoreCase(StringFilter filter) {
        if (filter.getOr() != null) {
            return filter.getOr().getNotContainsIgnoreCase();
        }
        return null;
    }

    private String getAndStartWithIgnoreCase(StringFilter filter) {
        String value = filter.getStartWithIgnoreCase();
        if (value == null && filter.getAnd() != null) {
            value = filter.getAnd().getNotContainsIgnoreCase();
        }
        return value;
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
        String value = filter.getEndWithIgnoreCase();
        if (value == null && filter.getOr() != null) {
            value = filter.getOr().getEndWithIgnoreCase();
        }
        return value;
    }
}
