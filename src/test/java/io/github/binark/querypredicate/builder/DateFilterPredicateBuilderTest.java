package io.github.binark.querypredicate.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.mockito.Answers;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.Date;

class DateFilterPredicateBuilderTest {
    protected static final String AND = "AND";
    protected static final String OR = "OR";

  @Mock(answer = Answers.RETURNS_SELF)
  protected Path path;

  @Mock(answer = Answers.RETURNS_MOCKS)
  protected SessionFactoryImpl sessionFactory;

    protected CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    protected DateFilterPredicateBuilder predicateBuilder = new DateFilterPredicateBuilder();

    protected Date atEndOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTime();
  }

    protected Date atStartOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
}