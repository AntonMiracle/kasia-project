package com.kasia.service.validation.message;

public enum AMessageLink implements ModelValidationMessageLink {
    ID_NEGATIVE(ID_LINK)
    ,NAME_NULL("{articleNameNull}")
    ,TYPE_NULL("{articleTypeNull}")
    ,DESCRIPTION_NULL("{articleDescriptionNull}")

    ;

    private final String link;

    AMessageLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }
}
