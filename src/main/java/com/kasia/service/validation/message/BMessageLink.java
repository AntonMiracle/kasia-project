package com.kasia.service.validation.message;

public enum BMessageLink implements ModelValidationMessageLink {
    ID_NEGATIVE(ID_LINK)
    , BALANCE_NULL("{budgetBalanceNull}")
    , NAME_NULL("{budgetNameNull}")
    , NAME_REGEX_ERROR("{budgetNameRegexError}")
    , CURRENCY_NULL("{budgetCurrencyNull}")
    , OPERATIONS_NULL("{budgetOperationsNull}")
    , CREATE_ON_NULL(CREATE_ON_NULL_LINK)
    , CURRENCY_NOT_AVAILABLE("{budgetCurrencyNotAvailable}");

    private final String link;

    BMessageLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }
}
