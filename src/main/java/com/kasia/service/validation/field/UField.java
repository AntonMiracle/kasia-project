package com.kasia.service.validation.field;

public enum UField implements ModelField {
    ID(ID_FIELD_NAME), EMAIL("email"), PASSWORD("password")
    , NICK("nick"), ROLES("roles"), BUDGETS("budgets"), ARTICLES("articles")
    , EMPLOYERS("employers"), CREATE_ON("createOn"), ZONE_ID("zoneId");

    private String name;

    UField(String field) {
        this.name = field;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ModelField[] getAll() {
        return UField.values();
    }


}
