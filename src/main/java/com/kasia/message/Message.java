package com.kasia.message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Locale;

public enum Message {
    PASSWORD_REGEX_ERROR("passwordRegexError", Bundle.LOGIN_REGISTRATION)
    , CONFIRM_ERROR("passwordConfirmError", Bundle.LOGIN_REGISTRATION)
    , EMAIL_REGEX_ERROR("emailRegexError", Bundle.LOGIN_REGISTRATION)
    , EMAIL_EXIST("emailExist", Bundle.LOGIN_REGISTRATION)
    , NICK_REGEX_ERROR("nickRegexError", Bundle.LOGIN_REGISTRATION)
    , NICK_EXIST("nickExist", Bundle.LOGIN_REGISTRATION)
    , REGISTRATION_SUCCESS("registrationSuccess", Bundle.LOGIN_REGISTRATION)
    , LOGIN_ERROR("loginError", Bundle.LOGIN_REGISTRATION)

    ;

    private final Bundle bundle;
    private final String link;

    Message(String link, Bundle bundle) {
        this.link = link;
        this.bundle = bundle;
    }

    public String get() {
        return get(Locale.getDefault());
    }

    public String get(Locale locale) {
        return bundle.get(locale).getString(link);
    }

    public FacesMessage get(FacesContext facesContext) {
        return new FacesMessage(get(facesContext.getViewRoot().getLocale()));
    }

}
