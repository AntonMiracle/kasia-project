package com.kasia.service.validation.field;

public enum EField implements ModelField {
    ID(ID_FIELD_NAME), NAME("name"), DESCRIPTION("description");

    private final String name;

    EField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public ModelField[] getAll() {
        return EField.values();
    }
}
