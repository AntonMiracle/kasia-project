package com.kasia.model.validation;

public enum FieldName {
    USER_PASSWORD("password")
    , USER_NAME("name")
    , USER_EMAIL("email")
    , BUDGET_NAME("name")
    , ELEMENT_PROVIDER_NAME("name")
    , ELEMENT_NAME("name")

    , USER_DTO_EMAIL("email")
    , USER_DTO_NAME("name")
    , USER_DTO_PASSWORD("password")
    , USER_DTO_CONFIRM("confirm")
    , USER_DTO_ZONEID("zoneId")
    , USER_DTO_LANG("lang")
    , USER_DTO_COUNTRY("country")

    ;

    private final String name;

    FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
