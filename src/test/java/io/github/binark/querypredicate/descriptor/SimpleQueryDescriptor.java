package io.github.binark.querypredicate.descriptor;

import io.github.binark.querypredicate.filter.BaseDateFilter;
import io.github.binark.querypredicate.filter.BaseLocalDateFilter;
import io.github.binark.querypredicate.filter.BaseStringFilter;

public class SimpleQueryDescriptor implements QueryDescriptor{
    private BaseStringFilter id;

    private BaseDateFilter birthday;

    private BaseLocalDateFilter createdAt;

    public BaseStringFilter getId() {
        return id;
    }

    public void setId(BaseStringFilter id) {
        this.id = id;
    }

    public BaseDateFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(BaseDateFilter birthday) {
        this.birthday = birthday;
    }

    public BaseLocalDateFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            BaseLocalDateFilter createdAt) {
        this.createdAt = createdAt;
    }
}
