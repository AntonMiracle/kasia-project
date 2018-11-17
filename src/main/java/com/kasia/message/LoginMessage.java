package com.kasia.message;

import java.util.Locale;

public enum LoginMessage implements Message {
    LOGIN_ERROR("loginError", Bundle.LOGIN_REGISTRATION);

    private final Bundle bundle;
    private final String link;

    LoginMessage(String link, Bundle bundle) {
        this.link = link;
        this.bundle = bundle;
    }

    @Override
    public String get() {
        return get(Locale.getDefault());
    }

    @Override
    public String get(Locale locale) {
        return bundle.get(locale).getString(link);
    }
}
