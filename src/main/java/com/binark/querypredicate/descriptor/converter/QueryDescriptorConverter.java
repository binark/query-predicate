package com.binark.querypredicate.descriptor.converter;

import com.binark.querypredicate.annotation.EntityFieldName;
import com.binark.querypredicate.builder.PredicateBuilder;
import com.binark.querypredicate.descriptor.QueryDescriptor;
import com.binark.querypredicate.filter.Filter;
import com.binark.querypredicate.management.PredicateBuilderResolver;
import jakarta.persistence.criteria.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QueryDescriptorConverter<Q extends QueryDescriptor>{

        Logger logger = Logger.getLogger(QueryDescriptorConverter.class.getSimpleName());

        private final PredicateBuilderResolver predicateBuilderResolver;

        public QueryDescriptorConverter() {
        predicateBuilderResolver = new PredicateBuilderResolver();
        }

        public QueryDescriptorConverter(PredicateBuilderResolver predicateBuilderResolver) {
                this.predicateBuilderResolver = predicateBuilderResolver;
        }

        public List<Predicate> convert(Root root, CriteriaBuilder builder, Q queryDescriptor) {

                List<Predicate> predicates = new ArrayList<>();

                List<Field> queryDescriptorFields = getQueryDescriptorFields(queryDescriptor.getClass());

                List<Field> filterFields = queryDescriptorFields.stream().filter(field -> Filter.class.isAssignableFrom(field.getType())).collect(Collectors.toList());

                filterFields.forEach(field -> predicates.add(getPredicateFromField(field, root, builder, queryDescriptor)));

                List<Field> subQueryDescriptorFields = queryDescriptorFields.stream().filter(field -> QueryDescriptor.class.isAssignableFrom(field.getType())).collect(Collectors.toList());

                for (Field field : subQueryDescriptorFields) {
                        field.setAccessible(true);
                    try {
                            Q subQueryDescriptorValue = (Q)field.get(queryDescriptor);
                            predicates.addAll(joinDescriptor(root.join(field.getName()), builder, subQueryDescriptorValue));
                    } catch (IllegalAccessException e) {
                            e.printStackTrace();
                    }

                }

                return predicates;
        }

        private List<Predicate> joinDescriptor(Join joinRoot, CriteriaBuilder builder, Q queryDescriptor) {

                List<Predicate> predicates = new ArrayList<>();

                List<Field> queryDescriptorFields = getQueryDescriptorFields(queryDescriptor.getClass());

                List<Field> filterFields = queryDescriptorFields.stream().filter(field -> Filter.class.isAssignableFrom(field.getType())).collect(Collectors.toList());

                filterFields.forEach(field -> predicates.add(getPredicateFromField(field, joinRoot, builder, queryDescriptor)));

                List<Field> subQueryDescriptorFields = queryDescriptorFields.stream().filter(field -> QueryDescriptor.class.isAssignableFrom(field.getType())).collect(Collectors.toList());

                for (Field field : subQueryDescriptorFields) {
                        field.setAccessible(true);
                        try {
                                Q subQueryDescriptorValue = (Q)field.get(queryDescriptor);
                                predicates.addAll(joinDescriptor(joinRoot.join(field.getName()), builder, subQueryDescriptorValue));
                        } catch (IllegalAccessException e) {
                                e.printStackTrace();
                        }

                }

                return predicates;
        }


        private Predicate getPredicateFromField(Field field, Path root, CriteriaBuilder criteriaBuilder, QueryDescriptor queryDescriptor) {
                if(Filter.class.isAssignableFrom(field.getType())) {
                        try {
                                field.setAccessible(true);
                                Object value = field.get(queryDescriptor);
                                if (value != null) {
                                        PredicateBuilder predicateBuilder = predicateBuilderResolver.resolverPredicateBuilder(value.getClass());
                                        if (value.getClass().getAnnotation(EntityFieldName.class) != null) {
                                                return predicateBuilder.buildPredicate(root, criteriaBuilder, (Filter) value);
                                        } else {
                                                return predicateBuilder.buildPredicate(root, criteriaBuilder, (Filter) value, field.getName());
                                        }
                                }

                        } catch (IllegalAccessException iae) {
                                logger.log (Level.WARNING, "could not access field name: {}", field.getName());
                                iae.printStackTrace();
                        }
                }

                return criteriaBuilder.and();
        }

        private List<Field> getQueryDescriptorFields(Class queryDescriptorClass) {

                Field[] declaredFields = queryDescriptorClass.getDeclaredFields();
                List<Field> fields = Arrays.asList(declaredFields);

                Class<?> superclass = queryDescriptorClass.getSuperclass();

                if (QueryDescriptor.class.isAssignableFrom(superclass)) {
                        fields.addAll(getQueryDescriptorFields(superclass));
                }

                return fields.stream()
                             .filter(field ->
                                     Filter.class.isAssignableFrom(field.getType()) ||
                                             QueryDescriptor.class.isAssignableFrom(field.getType()))
                             .collect(Collectors.toList());
        }
}
