package com.kasia.controller;

final class ViewName {
    static final String PROFILE = "profile";
    static final String HOME = "home";
    static final String LOGIN = "login";
    static final String REGISTRATION = "registration";
    static final String BUDGET = "budget";
    static final String BUDGET_ADD = "budgetAdd";

    static String redirect(String view) {
        return "redirect:" + view;
    }

}
