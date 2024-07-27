package io.github.binark.querypredicate.descriptor;


import io.github.binark.querypredicate.filter.DateFilter;
import io.github.binark.querypredicate.filter.LocalDateFilter;
import io.github.binark.querypredicate.filter.StringFilter;

public class SimpleQueryDescriptor implements QueryDescriptor{
    private StringFilter id;

    private DateFilter birthday;

    private LocalDateFilter createdAt;

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public DateFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(DateFilter birthday) {
        this.birthday = birthday;
    }

    public LocalDateFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
        LocalDateFilter createdAt) {
        this.createdAt = createdAt;
    }
}
