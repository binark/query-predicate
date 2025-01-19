package io.github.binark.querypredicate.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

@ExtendWith(MockitoExtension.class)
class InstantFilterPredicateBuilderTest {

    protected static final String AND = "AND";
    protected static final String OR = "OR";

    @Mock(answer = Answers.RETURNS_SELF)
    protected Path path;

    @Mock(answer = Answers.RETURNS_MOCKS)
    protected SessionFactoryImpl sessionFactory;

    protected CriteriaBuilder criteriaBuilder = new CriteriaBuilderImpl(sessionFactory);

    protected InstantFilterPredicateBuilder predicateBuilder = new InstantFilterPredicateBuilder();

    protected Instant getStartOfDay(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault()).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    protected Instant getEndOfDay(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault()).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
    }
}