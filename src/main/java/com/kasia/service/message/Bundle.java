package com.kasia.service.message;

public enum Bundle {
    REGISTRATION("RegistrationMessage");
    private final String name;

    Bundle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
