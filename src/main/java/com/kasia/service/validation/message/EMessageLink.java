package com.kasia.service.validation.message;

public enum EMessageLink implements ModelValidationMessageLink {

    ID_NEGATIVE(ID_LINK), NAME_NULL("{employerNameNull}");

    private final String link;

    EMessageLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }
}
