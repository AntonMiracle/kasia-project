package com.kasia.page;

public enum Attribute {
    LOGIN_INFORMATION("information")
    , SESSION_USER_ATTRIBUTE_NAME("kasia")
    ;

    private final String name;

    Attribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
