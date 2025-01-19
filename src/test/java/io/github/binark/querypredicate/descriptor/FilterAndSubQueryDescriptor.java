package io.github.binark.querypredicate.descriptor;

import io.github.binark.querypredicate.filter.BaseStringFilter;

public class FilterAndSubQueryDescriptor implements QueryDescriptor{

    private BaseStringFilter firstName;

    private SimpleQueryDescriptor subField;

    public BaseStringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(BaseStringFilter firstName) {
        this.firstName = firstName;
    }

    public SimpleQueryDescriptor getSubField() {
        return subField;
    }

    public void setSubField(SimpleQueryDescriptor subField) {
        this.subField = subField;
    }
}
