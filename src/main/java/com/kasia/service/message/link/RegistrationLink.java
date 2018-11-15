package com.kasia.service.message.link;

import com.kasia.service.message.Bundle;

public enum RegistrationLink implements MessageLink {
        NICK_EXIST("nickExist")
    , NICK_ERROR("nickError")
    , PASSWORD_ERROR("passwordError")
    , PASSWORD_CONFIRM_ERROR("PasswordConfirmError")
    , ZONE_NOT_EXIST("zoneNotExist")
    , EMAIL_EXIST("emailExist")
    , EMAIL_REGEX_ERROR("emailError");

    private final String link;
    private final Bundle bundle = Bundle.REGISTRATION;

    RegistrationLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }
}
