package com.kasia.service.validation.field;

public enum AField implements ModelField {
    ID(ID_FIELD_NAME), NAME("name"), DESCRIPTION("description"), TYPE("type");

    private final String name;

    AField(String field) {
        this.name = field;
    }

    @Override
    public String getName() {
        return name;
    }

}
