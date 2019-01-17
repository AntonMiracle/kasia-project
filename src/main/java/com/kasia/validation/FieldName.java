package com.kasia.validation;

public enum FieldName {
    USER_PASSWORD("password")
    , USER_NAME("name")
    , USER_EMAIL("email")
    ;

    private final String name;

    FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
