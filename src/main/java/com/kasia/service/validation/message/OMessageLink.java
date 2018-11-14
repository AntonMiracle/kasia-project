package com.kasia.service.validation.message;

public enum OMessageLink implements ModelValidationMessageLink {
    ID_NEGATIVE(ID_LINK)
    , AMOUNT_NULL("{operationAmountNull}")
    , USER_NULL("{operationUserNull}")
    , ARTICLE_NULL("{operationArticleNull}")
    , EMPLOYER_NULL("{operationEmployerNull}")
    , CREATE_ON_NULL(CREATE_ON_NULL_LINK)

    ;

    private final String link;

    OMessageLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }
}
