package com.kasia.service.validation.field;

public enum OField implements ModelField {
    ID(ID_FIELD_NAME), AMOUNT("amount"), ARTICLE("article"), USER("user"), EMPLOYER("employer"), CREATE_ON("createOn");

    private final String name;

    OField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
