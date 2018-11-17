package com.kasia.message;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Bundle {
    LOGIN_REGISTRATION("LoginRegistrationMessage");
    private final String name;

    Bundle(String name) {
        this.name = name;
    }

    public ResourceBundle get(Locale locale) {
        return ResourceBundle.getBundle(name, locale);
    }
}
