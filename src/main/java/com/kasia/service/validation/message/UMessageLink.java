package com.kasia.service.validation.message;

public enum UMessageLink implements ModelValidationMessageLink {
    ID_NEGATIVE(ID_LINK)
    , ZONE_ID_NULL("{userZoneIdNull}")
    , CREATE_ON_NULL(CREATE_ON_NULL_LINK)
    , ARTICLES_NULL("{userArticlesNull}")
    , BUDGETS_NULL("{userBudgetsNull}")
    , EMPLOYERS_NULL("{userEmployersNull}")
    , PASSWORD_NULL("{userPasswordNull}")
    , PASSWORD_REGEX_ERROR("{userPasswordDontMatchWithRegex}")
    , PASSWORD_CONFIRM_ERROR("{userPasswordNotEqualsWithConfirmPassword}")
    , ROLES_NULL("{userRolesNull}")
    , ROLES_NEEDED_USER_ROLE("{userRolesWithoutUserRole}")
    , EMAIL_NULL("{userEmailNull}")
    , EMAIL_REGEX_ERROR("{userEmailDoNotMatchWithRegex}")
    , NICK_NULL("{userNickNull}")
    , NICK_REGEX_ERROR("{userNickDontMatchWithRegex}")

    ;

    private final String link;

    UMessageLink(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return link;
    }
}
