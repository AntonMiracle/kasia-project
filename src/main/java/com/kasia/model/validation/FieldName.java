package com.kasia.model.validation;

public enum FieldName {
    USER_PASSWORD("password")
    , USER_NAME("name")
    , USER_EMAIL("email")
    , BUDGET_NAME("name")
    , ELEMENT_PROVIDER_NAME("name")
    , ELEMENT_NAME("name")
    ;

    private final String name;

    FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
