package com.binark.querypredicate.descriptor;

import com.binark.querypredicate.filter.StringFilter;

public class SimpleQueryDescriptor implements QueryDescriptor{
    private StringFilter id;

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }
}
