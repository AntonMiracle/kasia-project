package com.kasia.controller.page.parameter;

public enum RegistrationLoginParameter implements Parameter {
    REGISTRATION_INFORMATION("registrationInformation");
    private final String name;

    RegistrationLoginParameter(String name) {
        this.name = name;
    }

    @Override
    public String get() {
        return name;
    }
}
