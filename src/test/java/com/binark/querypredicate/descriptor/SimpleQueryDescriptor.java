package com.binark.querypredicate.descriptor;

import com.binark.querypredicate.filter.DateFilter;
import com.binark.querypredicate.filter.LocalDateFilter;
import com.binark.querypredicate.filter.StringFilter;
import java.time.LocalDateTime;

public class SimpleQueryDescriptor implements QueryDescriptor{
    private StringFilter id;

    private DateFilter birthday;

    private LocalDateFilter<LocalDateTime> createdAt;

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

    public LocalDateFilter<LocalDateTime> getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
        LocalDateFilter<LocalDateTime> createdAt) {
        this.createdAt = createdAt;
    }
}
