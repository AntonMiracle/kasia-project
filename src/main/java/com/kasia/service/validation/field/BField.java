package com.kasia.service.validation.field;

public enum BField implements ModelField {
    ID(ID_FIELD_NAME), NAME("name"), OPERATIONS("operations"), BALANCE("balance"), CREATE_ON("createOn"), CURRENCY("currency");

    private final String name;

    BField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ModelField[] getAll() {
        return BField.values();
    }
}
