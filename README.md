![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.binark/query-predicate?server=https%3A%2F%2Fs01.oss.sonatype.org%2F&style=flat&logo=sonar&label=query-predicate)

# QUERY PREDICATE

QUERY PREDICATE is a package that builds JPA criteria predicates for the queries you want to perform.   
The goal is to describe the query conditions in a Java class and let the query predicate convert it to the JPA criteria predicate. So that you could use it to perform SQL queries. This package is built with **Java version 11**

## 1. INSTALLATION


## 1.2 Maven

* For Jakarta EE 9 +

```
<dependency>
	    <groupId>io.github.binark</groupId>
	    <artifactId>query-predicate</artifactId>
	    <version>${version}</version>
</dependency>
```

* For Java EE and Jakarta EE 8 -
```
<dependency>
    <groupId>io.github.binark</groupId>
    <artifactId>query-predicate-jee</artifactId>
    <version>${version}</version>
</dependency>
```

## 1.3 Maven

* For Jakarta EE 9 +

```
dependencies {
    implementation 'io.github.binark:query-predicate:${version}'
}
```

* For Java EE and Jakarta EE 8 -
```
dependencies {
    implementation 'io.github.binark:query-predicate-jee:${version}'
}
```

The latest version of query predicate is **1.1.0**

## 2. HOW IT WORKS

A short example:

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

QueryDescriptor queryDescriptor = new SimpleQueryDescriptor();
QueryDescriptorConverter converter = new QueryDescriptorConverter<>();

EntityManager entityManager = entityManagerFactory.createEntityManager();
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Foo> query = cb.createQuery(Foo.class);
Root<Foo> root = query.from(Foo.class);

List<Predicate> predicates = converter.convert(root, cb, queryDescriptor);
query.select(root).where(predicates.stream().toArray());
```

### 2.1 Query descriptor

A query descriptor is a representation of the query you want to perform for an entity. It contains the fields that are the conditions for the query. Each field should match with an entity field. Query predicate doesn't take care about the entity field names, it doesn't know the entity for which you want to build predicates. So if you use the wrong entity names, it will always generate predicates but the query will fail because of an unknown field. Each query descriptor should implement the **QueryDescriptor** interface.

A shirt example:

```java
class SimpleQueryDescriptor implements QueryDescriptor {

  private LongFilter id;

  private StringFilter lastName;

  private DateFilter birthday;
  
  ...
  // getters an setters
}
```

### 2.2 Filters

The filter is a class that the query condition for a particular entity field. The name of the filter attribute in the query descriptor should match the attribute name in the entity, but there is a way to do it differently.

```java
import jakarta.persistence.criteria.Predicate;
import java.util.List;

StringFilter lastName = new StringFilter();
lastName.setStartWithIgnoreCase("foo");

DateFilter birthday = new DateFilter();
birthday.setIsToday(true);

SimpleQueryDescriptor queryDescriptor = new SimpleQueryDescriptor();
queryDescriptor.setLastName(lastName);
queryDescriptor.setBirthday(birthday);

List<Predicate> predicates = converter.convert(root, cb, queryDescriptor);
query.select(root).where(predicates.stream().toArray());
```

The query above will find all the rows that have a _lastName_ that starts with "foo" (non-case-sensitive) and have _birthday_ for today. The query predicate doesn't take care about filters that have null value, they are simply ignored. So the query above will not use _id_ as a parameter. Anyway, if you want to perform a query with a null field condition, you should set the _isNull_ filter attribute to true

```java
StringFilter lastName = new StringFilter();
lastName.setIsNull(true);
```
This will find all records that have a null value as lastName

You can pass several conditions in a filter. When it happens, the converter treats those conditions with an **OR** operator

```java
StringFilter lastName = new StringFilter();
lastName.setStartWithIgnoreCase("foo");
lastName.setContains("bar");
```

This will find all records that have _lastName_ that starts with "foo" (non-case-sensitive) or _lastName_ that contains "bar" (case-sensitive) 

### 2.3 Field mapping

If you want to map a query descriptor field and entity field with a different name, you should use the **EntityFieldName** annotation

```java
class SimpleQueryDescriptor implements QueryDescriptor {

  @EntityFieldName("lastName")
  private StringFilter name;
  
  ...
  // getters an setters
}
```

You could use this annotation if you want to map many query descriptor fields to the same entity field

```java
class SimpleQueryDescriptor implements QueryDescriptor {

  @EntityFieldName("lastName")
  private StringFilter name;

  private StringFilter lastName;
  
  ...
  // getters an setters
}
```

If you convert this query descriptor, the converter will produce two predicates with conditions for the _lastName_ instead of one predicate with two expressions with an OR operator.

### 2.4 Join predicate

There is a way to get a join predicate with query predicate. You just need to a query descriptor as a field of another query descriptor.

```java
class SimpleQueryDescriptor implements QueryDescriptor {

  private StringFilter lastName;

  private OtherQueryDescriptor fieldName;
  ...
  // getters an setters
}
```

If you convert that query descriptor, the converter will understand that your entity has an association field with name _fieldName_, and then it will generate a join predicate for that field.

**limitations:**  
The _EntityFieldName_ annotation doesn't work with a field of type query descriptor, it only works for filter  
For the current version of query predicate, the only join type used is the _inner join_. We are working to allow other join types.

## 3. CUSTOMIZATION

Query predicate allows you to create your custom filters and predicate builders.

### 3.1 CUSTOM FILTER

```java
class MyFilter extends BaseFilter {
  // fields
}
```

There is no restriction for the fields in your filter. You should at least implement the **Filter**** interface. But it is recommended to extend the **BaseFilter**** class or another top-level filter class to inherit its fields. Anyway, if you want to create a custom filter from scratch, follow the example below

```java
class MyFilter implements Filter {
  // fields
}
```

If you use that filter in a query descriptor like that, the converter will not find the predicate builder that uses that filter. And will throw an exception: _"There is no predicate builder registered for the filter MyFilter"_. So you should also create a predicate builder for your filter

### 3.2 CUSTOM PREDICATE BUILDER

When you create a custom filter, you should create a custom predicate builder that will use that filter. If your filter implements the **Filter** interface, your predicate builder should implement the **PredicateBuilder** interface, and you are responsible for implementing all the processes from scratch. If your filter extends another filter, your predicate builder should extend the predicate builder for the parent filter so that your builder will reuse the parent features.

```java
import jakarta.persistence.criteria.CriteriaBuilder;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

@FilterClass(MyFilter.clss)
class MyFilterPredicateBuilder extends BaseFilterPredicateBuilder<MyFilter> {

  public Predicate buildPredicate(Path path, CriteriaBuilder builder, MyFilter filter, String fieldName) {
    List<Predicate> predicates = buildBaseFilterPredicate(path, builder, filter, fieldName);
    // you custom predicate builder actions

    return builder.or(predicates.stream().toArray());
  }
}
```

The _buildBaseFilterPredicate_ method comes from the **AbstractPredicateBuilder** which **BaseFilterPredicateBuilder** extends.

```java
import jakarta.persistence.criteria.CriteriaBuilder;
import java.nio.file.Path;
import java.util.function.Predicate;

@FilterClass(MyFilter.clss)
class MyFilterPredicateBuilder implements PredicateBuilder<MyFilter> {

  public Predicate buildPredicate(Path path, CriteriaBuilder builder, MyFilter filter, String fieldName) {
    // you custom predicate builder actions

    return predicate;
  }
}
```

When you implement the **PredicateBuilder** interface, you have to write all actions from scratch.  

You always need to put the **FilterClass** annotation with the filter class value. It is used for the predicate builder registration. Because after creating a predicate builder, you should register it.

### 3.3 REGISTER A CUSTOM PREDICATE BUILDER

Query predicate has a predicate builder registry that you should use to register your custom predicate builder, otherwise the converter will not find it.

```java
PredicateBuilderRegistry registry = new PredicateBuilderRegistry();
registry.registerPredicateBuilder(new MyFilterPredicateBuilder());
```

After that, the converter will find it when you want to generate predicates.  

Hope this package will help you.
