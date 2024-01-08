package io.github.binark.querypredicate.descriptor.converter;

import io.github.binark.querypredicate.annotation.EntityFieldName;
import io.github.binark.querypredicate.builder.PredicateBuilder;
import io.github.binark.querypredicate.descriptor.QueryDescriptor;
import io.github.binark.querypredicate.filter.Filter;
import io.github.binark.querypredicate.management.BasePredicateBuilderResolver;
import io.github.binark.querypredicate.management.PredicateBuilderResolver;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @param <Q> generic param that should implements {@link QueryDescriptor}
 * @author kenany (armelknyobe@gmail.com)
 * <p>
 * The query descriptor converter. The goal of that class is to convert a {@link QueryDescriptor} to
 * a {@link List} of {@link Predicate} following the {@link Filter} rules. If the query descriptor
 * has a child query descriptor, the join query predicate will be created for that field.
 */
public class QueryDescriptorConverter<Q extends QueryDescriptor> {

  Logger logger = Logger.getLogger(QueryDescriptorConverter.class.getSimpleName());

  private final PredicateBuilderResolver predicateBuilderResolver;

  /**
   * Default constructor
   */
  public QueryDescriptorConverter() {
    predicateBuilderResolver = new BasePredicateBuilderResolver();
  }

  /**
   * Constructor with a custom predicate builder resolver
   * @param predicateBuilderResolver The custom predicate builder resolver implementation
   */
  public QueryDescriptorConverter(PredicateBuilderResolver predicateBuilderResolver) {
    this.predicateBuilderResolver = predicateBuilderResolver;
  }

  /**
   * Convert the query descriptor to a {@link List} of {@link Predicate}
   *
   * @param root            {@link Root} The criteria root
   * @param builder         {@link CriteriaBuilder} The criteria builder
   * @param queryDescriptor The query descriptor should implement {@link QueryDescriptor}
   * @return The {@link List} of {@link Predicate} according to the filter rules
   */
  public List<Predicate> convert(Root root, CriteriaBuilder builder, Q queryDescriptor) {

    List<Predicate> predicates = new ArrayList<>();

    List<Field> queryDescriptorFields = extractQueryDescriptorFields(queryDescriptor.getClass());

    List<Field> filterFields = queryDescriptorFields.stream()
        .filter(field -> Filter.class.isAssignableFrom(field.getType()))
        .collect(Collectors.toList());

    for (Field field : filterFields) {
      Predicate predicate = getPredicateFromField(field, root, builder, queryDescriptor);
      if (predicate != null) {
        predicates.add(predicate);
      }
    }

    List<Field> subQueryDescriptorFields = queryDescriptorFields.stream()
        .filter(field -> QueryDescriptor.class.isAssignableFrom(field.getType()))
        .collect(Collectors.toList());

    for (Field field : subQueryDescriptorFields) {
      field.setAccessible(true);
      try {
        Q subQueryDescriptorValue = (Q) field.get(queryDescriptor);
        if (subQueryDescriptorValue != null) {
          predicates.addAll(
              joinDescriptor(root.join(field.getName()), builder,
                  subQueryDescriptorValue));
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

    }

    return predicates;
  }

  /**
   * Create a join predicates for the child query descriptor
   *
   * @param joinRoot        {@link Join} The criteria join
   * @param builder         {@link CriteriaBuilder} The criteria builder
   * @param queryDescriptor The child query descriptor
   * @return The joined {@link List} of {@link Predicate}
   */
  private List<Predicate> joinDescriptor(Join joinRoot, CriteriaBuilder builder,
      Q queryDescriptor) {

    List<Predicate> predicates = new ArrayList<>();

    List<Field> queryDescriptorFields = extractQueryDescriptorFields(queryDescriptor.getClass());

    List<Field> filterFields = queryDescriptorFields.stream()
        .filter(field -> Filter.class.isAssignableFrom(field.getType()))
        .collect(Collectors.toList());

    for (Field field : filterFields) {
      Predicate predicate = getPredicateFromField(field, joinRoot, builder, queryDescriptor);
      if (predicate != null) {
        predicates.add(predicate);
      }
    }

    List<Field> subQueryDescriptorFields = queryDescriptorFields.stream()
        .filter(field -> QueryDescriptor.class.isAssignableFrom(field.getType()))
        .collect(Collectors.toList());

    for (Field field : subQueryDescriptorFields) {
      field.setAccessible(true);
      try {
        Q subQueryDescriptorValue = (Q) field.get(queryDescriptor);
        predicates.addAll(
            joinDescriptor(joinRoot.join(field.getName()), builder, subQueryDescriptorValue));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

    }

    return predicates;
  }

  /**
   * Create a {@link Predicate} for a query descriptor field which is a {@link Filter} type
   *
   * @param field           {@link Field} The field
   * @param path            {@link Path} The criteria path
   * @param criteriaBuilder {@link CriteriaBuilder} The criteria builder
   * @param queryDescriptor The query descriptor
   * @return The {@link Predicate} the follows the rules of that field
   */
  private Predicate getPredicateFromField(Field field, Path path, CriteriaBuilder criteriaBuilder,
      QueryDescriptor queryDescriptor) {
    Predicate predicate = null;
    if (Filter.class.isAssignableFrom(field.getType())) {
      try {
        field.setAccessible(true);
        Object value = field.get(queryDescriptor);
        if (value != null) {
          PredicateBuilder predicateBuilder = predicateBuilderResolver.resolverPredicateBuilder(
              value.getClass());
          String fieldName = field.getName();
          if (field.isAnnotationPresent(EntityFieldName.class)) {
            fieldName = predicateBuilder.getFieldNameFromAnnotation(field);
          }
          predicate = predicateBuilder.buildPredicate(path, criteriaBuilder, (Filter) value, fieldName);
        }

      } catch (IllegalAccessException iae) {
        logger.log(Level.WARNING, "could not access field name: {}", field.getName());
        iae.printStackTrace();
      }
    }

    return predicate;
  }

  /**
   * Extract the query descriptor field. This method will recursively extract the field from the
   * parent query descriptor class, if the current query descriptor if a child. But It will just
   * return the fields that implements {@link Filter} or {@link QueryDescriptor}
   *
   * @param queryDescriptorClass The query descriptor class
   * @return All query descriptor fields includes parents fields
   */
  private List<Field> extractQueryDescriptorFields(Class queryDescriptorClass) {

    Field[] declaredFields = queryDescriptorClass.getDeclaredFields();
    List<Field> fields = Arrays.asList(declaredFields);

    Class<?> superclass = queryDescriptorClass.getSuperclass();

    if (QueryDescriptor.class.isAssignableFrom(superclass)) {
      fields.addAll(extractQueryDescriptorFields(superclass));
    }

    return fields.stream()
        .filter(field ->
            Filter.class.isAssignableFrom(field.getType()) ||
                QueryDescriptor.class.isAssignableFrom(field.getType()))
        .collect(Collectors.toList());
  }
}
